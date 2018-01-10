package com.example.marcin.meetfriends.ui.planned_event_detail.event_questionnaire

import android.location.LocationManager
import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.DateVote
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.models.Questionnaire
import com.example.marcin.meetfriends.models.VenueVote
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.params.EventIdParams
import com.example.marcin.meetfriends.ui.common.params.PlaceIdParams
import com.example.marcin.meetfriends.ui.common.use_cases.GetDeviceLocationUseCase
import com.example.marcin.meetfriends.ui.common.use_cases.GetFilledQuestionnairesUseCase
import com.example.marcin.meetfriends.ui.common.use_cases.GetNearbyPlacesUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-05.
 */
@ScreenScope
class EventQuestionnairePresenter @Inject constructor(
    private val auth: FirebaseAuth,
    private val locationManager: LocationManager,
    private val firebaseDatabase: FirebaseDatabase,
    private val eventBasicInfoParams: EventBasicInfoParams,
    private val selectedVenueStorage: SelectedVenueStorage,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase,
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase
) : BasePresenter<EventQuestionnaireContract.View>(), EventQuestionnaireContract.Presenter {

  private var currentLocation = Location(0.0, 0.0)

  private val venuesList = mutableListOf<FirebasePlace>()

  override fun onViewCreated() {
    super.onViewCreated()
    getCurrentLocation()
    getVenues()
  }

  override fun resume() {
    super.resume()
    view.setUpAdapterListeners()
    checkFilledQuestionnaire()
  }

  override fun clickedChartsButton() {
    view.startChartsDialogFragment(EventIdParams(eventBasicInfoParams.event.id.let { it!! }))
  }

  override fun onClickSelectedVenue() {
    view.startPlaceDetailsActivity(PlaceIdParams(placeId = selectedVenueStorage.get(eventBasicInfoParams.event.id.let { it!! }).let { it!! }))
  }

  private fun checkFilledQuestionnaire() {
    val disposable = getFilledQuestionnairesUseCase.get(eventBasicInfoParams.event.id.let { it!! })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { showQuestionnairesProgressBars() }
        .doFinally { hideQuestionnairesProgressBars() }
        .subscribe({ questionnaires ->
          if (ifQuestionnairesExisting(questionnaires)) {
            checkDateQuestionnaire(questionnaires)
            checkVenueQuestionnaire(questionnaires)
          } else {
            view.showDateChooserLayout()
            view.showVenueChoserLayout()
          }
        })
    disposables?.add(disposable)
  }

  private fun checkVenueQuestionnaire(questionnaires: Any) {
    val currentUserVenueVote = (questionnaires as Questionnaire).venueQuestionnaire?.values?.firstOrNull { it.userId == auth.currentUser?.uid }
    if (currentUserVenueVote != null) {
      view.showFilledVenueQuestionnaire(venuesList.first { it.id == currentUserVenueVote.venueId })
    } else {
      view.showVenueChoserLayout()
    }
  }

  private fun checkDateQuestionnaire(questionnaires: Any) {
    val currentUserDateVote = (questionnaires as Questionnaire).dateQuestionnaire?.values?.firstOrNull { it.userId == auth.currentUser?.uid }
    if (currentUserDateVote != null) {
      view.showFilledDateQuestionnaire(DateTime(currentUserDateVote.timestamp?.toLong()))
    } else {
      view.showDateChooserLayout()
    }
  }

  private fun ifQuestionnairesExisting(questionnaires: Any) = questionnaires != -1

  private fun hideQuestionnairesProgressBars() {
    view.hideDateSectionProgressBar()
    view.hideVenuesSectionProgressBar()
  }

  private fun showQuestionnairesProgressBars() {
    view.showDateSectionProgressBar()
    view.showVenuesSectionProgressBar()
  }

  fun getCurrentLocation() {
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      view.buildAlertMessageNoGps()
    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      currentLocation = getDeviceLocationUseCase.get(view.getActivity())
    }
  }

  private fun getVenues() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventBasicInfoParams.event.id)
            .child(Constants.FIREBASE_VENUES))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          venuesList.add(dataSnapshot.value.getValue(FirebasePlace::class.java).let { it!! })
          venuesList.forEach { getDistanceMatrix(it) }
        })
    disposables?.add(disposable)
  }

  private fun getDistanceMatrix(venue: FirebasePlace) {
    val disposable = getNearbyPlacesUseCase
        .getDistanceMatrix("${currentLocation.lat},${currentLocation.lng}", venue.latLng.let { it!! })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showVenuesSectionProgressBar() }
        .doFinally {
          view.hideVenuesSectionProgressBar()
          view.initializeVenuesAdapter(venuesList.sortedBy { it.distance?.value })
        }
        .subscribe({ distance ->
          venue.distance = distance.rows.first().elements.first().distance
        })
    disposables?.add(disposable)
  }

  override fun sendDateVote(selectedDate: DateTime) {
    val disposable = RxFirebaseDatabase
        .setValue(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventBasicInfoParams.event.id)
            .child(Constants.FIREBASE_QUESTIONNAIRE)
            .child(Constants.FIREBASE_DATE_QUESTIONNAIRE)
            .child(auth.currentUser?.uid.let { it!! }),
            DateVote(
                userId = auth.currentUser?.uid,
                timestamp = selectedDate.millis.toString())
        )
        .doFinally {
          view.showChosenDateSnackBar(selectedDate, auth.uid!!)
          view.showFilledDateQuestionnaire(selectedDate)
        }
        .subscribe()
    disposables?.add(disposable)
  }

  override fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String) {
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
        .child(eventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_QUESTIONNAIRE)
        .child(Constants.FIREBASE_DATE_QUESTIONNAIRE)
        .orderByValue()
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            dataSnapshot.ref.child(dataSnapshot.children.firstOrNull { it.key == userId }?.key.toString()).removeValue()
          }

          override fun onCancelled(p0: DatabaseError?) {
            Timber.d("Canncelled remove participant with id $userId")
          }
        })
  }

  override fun handleChosenPlace(clickEvent: Observable<FirebasePlace>) {
    val disposable = clickEvent.subscribe({ place ->
      view.startPlaceDetailsActivity(PlaceIdParams(placeId = place.id.let { it!! }))
    })
    disposables?.add(disposable)
  }

  override fun handleClickedActionButton(clickedActionButtonEvent: Observable<FirebasePlace>) {
    val disposable = clickedActionButtonEvent.subscribe({ venue ->
      sendVenueVote(venue)
    })
    disposables?.add(disposable)
  }

  override fun removeChosenVenueFromEvent(venueId: String, userId: String) {
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
        .child(eventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_QUESTIONNAIRE)
        .child(Constants.FIREBASE_VENUE_QUESTIONNAIRE)
        .orderByValue()
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            dataSnapshot.ref.child(dataSnapshot.children.firstOrNull { it.key == userId }?.key.toString()).removeValue()
          }

          override fun onCancelled(p0: DatabaseError?) {
            Timber.d("Canncelled remove participant with id $userId")
          }
        })
  }

  override fun clickedChangeDateButton() {
    view.showDateChoserLayout()
  }

  override fun clickedChangeVenueButton() {
    view.showVenueChoserLayout()
  }

  fun sendVenueVote(venue: FirebasePlace) {
    val disposable = RxFirebaseDatabase
        .setValue(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventBasicInfoParams.event.id)
            .child(Constants.FIREBASE_QUESTIONNAIRE)
            .child(Constants.FIREBASE_VENUE_QUESTIONNAIRE)
            .child(auth.currentUser?.uid.let { it!! }),
            VenueVote(
                userId = auth.currentUser?.uid.let { it!! },
                venueId = venue.id.let { it!! })
        )
        .doFinally {
          selectedVenueStorage.add(eventBasicInfoParams.event.id.let { it!! }, venue.id.let { it!! })
          view.showChosenVenueSnackBar(venue, auth.uid!!)
          view.showFilledVenueQuestionnaire(venue)
        }
        .subscribe()
    disposables?.add(disposable)
  }
}