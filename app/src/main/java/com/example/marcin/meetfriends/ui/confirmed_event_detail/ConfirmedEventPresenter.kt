package com.example.marcin.meetfriends.ui.confirmed_event_detail

import android.content.res.Resources
import android.location.LocationManager
import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.params.EventIdParams
import com.example.marcin.meetfriends.ui.common.params.PlaceIdParams
import com.example.marcin.meetfriends.ui.common.use_cases.GetDeviceLocationUseCase
import com.example.marcin.meetfriends.ui.common.use_cases.GetNearbyPlacesUseCase
import com.example.marcin.meetfriends.ui.common.use_cases.GetParticipantsUseCase
import com.example.marcin.meetfriends.ui.planned_event_detail.viewmodel.EventBasicInfo
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2018-01-03.
 */
@ScreenScope
class ConfirmedEventPresenter @Inject constructor(
    private val auth: FirebaseAuth,
    private val eventIdParams: EventIdParams,
    private val locationManager: LocationManager,
    private val firebaseDatabase: FirebaseDatabase,
    private val getEventVenueUseCase: GetEventVenueUseCase,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val getConfirmedEventUseCase: GetConfirmedEventUseCase,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase
) : BasePresenter<ConfirmedEventContract.View>(), ConfirmedEventContract.Presenter {

  private var currentLocation = Location(0.0, 0.0)
  lateinit var eventVenue: FirebasePlace
  lateinit var event: Event

  override fun onViewCreated() {
    super.onViewCreated()
    getCurrentLocation()
    loadEvent()
  }

  private fun loadEvent() {
    val disposable = getConfirmedEventUseCase.get(eventIdParams.eventId)
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
        .doFinally { view.hideProgressBar() }
        .subscribe({ event ->
          this.event = event
          loadAllEventData(event)
        })
    disposables?.add(disposable)
  }

  override fun navigateToEventChat() {
    view.startEventChatActivity(
        EventBasicInfoParams(EventBasicInfo(
            id = event.id,
            name = event.name,
            organizerId = event.organizerId)
        )
    )
  }

  override fun onDeleteClicked(resources: Resources) {
    if (auth.currentUser?.uid == event.organizerId) {
      view.openDeleteButtonDialog(resources.getString(R.string.do_you_really_want_to_delete_this_event))
    } else {
      view.openDeleteButtonDialog(resources.getString(R.string.do_you_really_want_to_leave_this_event))
    }
  }

  override fun clickedVenueCardView() {
    view.openPlacDetailsActivity(PlaceIdParams(eventVenue.id.let { it!! }))
  }

  override fun clickedOpenGoogleMapsButton() {
    view.startNavigateToEventVenue("${currentLocation.lat},${currentLocation.lat}", eventVenue.latLng.let { it!! })
  }

  override fun deleteEvent() {
    view.startMainActivity()
    if (auth.currentUser?.uid == event.organizerId) {
      firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(event.id).removeValue()
    } else {
      firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
          .child(event.id.let { it!! })
          .child(Constants.FIREBASE_PARTICIPANTS)
          .orderByValue()
          .equalTo(auth.currentUser?.uid)
          .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
              dataSnapshot.ref.child(dataSnapshot.children.firstOrNull { it.value == auth.currentUser?.uid }?.key.toString()).removeValue()
            }

            override fun onCancelled(p0: DatabaseError?) {
              Timber.d("Canncelled remove participant with id ${auth.currentUser?.uid}")
            }
          })
    }
  }

  fun getCurrentLocation() {
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      view.buildAlertMessageNoGps()
    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      currentLocation = getDeviceLocationUseCase.get(view.getActivity())
    }
  }

  private fun loadAllEventData(event: Event) {
    val disposable = getParticipantsUseCase.getUserById(event.organizerId.let { it!! })
        .mergeWith { getEventParticipants(event.participants.map { it.value }) }
        .mergeWith { getEventVenue(event) }
        .subscribe({ organizer ->
          view.showEventData(event)
          view.showOrganizerData(organizer)
        })
    disposables?.add(disposable)
  }

  private fun getEventVenue(event: Event) {
    val disposable = getEventVenueUseCase.get(event)
        .subscribe({ venue ->
          eventVenue = venue
          getVenueDistanceMatrix(venue)
        })
    disposables?.add(disposable)
  }

  private fun getVenueDistanceMatrix(venue: FirebasePlace) {
    val disposable = getNearbyPlacesUseCase
        .getDistanceMatrix(
            origins = "${currentLocation.lat},${currentLocation.lng}",
            destinations = venue.latLng.let { it!! }
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ distance ->
          venue.distance = distance.rows.first().elements.first().distance
          view.showEventVenueCardView(venue)
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun getEventParticipants(participantsIds: List<String>) {
    val disposable = getParticipantsUseCase.getParticipantsByIds(participantsIds)
        .doOnSubscribe { view.showParticipantsProgressBar() }
        .doFinally { view.hideParticipantsProgressBar() }
        .subscribe({ participants ->
          view.showParticipantsRecyclerView(participants)
        })
    disposables?.add(disposable)
  }
}
