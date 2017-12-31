package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import android.location.LocationManager
import android.support.v4.app.FragmentActivity
import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.GetDeviceLocationUseCase
import com.example.marcin.meetfriends.ui.common.GetNearbyPlacesUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase
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
    private val getEventVenuesUseCase: GetEventVenuesUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase
) : BasePresenter<EventQuestionnaireContract.View>(), EventQuestionnaireContract.Presenter {
  private var currentLocation = Location(0.0, 0.0)

  val venuesList = mutableListOf<FirebasePlace>()
  override fun resume() {
    super.resume()
    getVenues()
  }

  override fun getCurrentLocation(activity: FragmentActivity?) {
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      view.buildAlertMessageNoGps()
    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      currentLocation = getDeviceLocationUseCase.get(view.getActivity())
    }
  }

  fun getVenues() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventBasicInfoParams.event.id)
            .child(Constants.FIREBASE_VENUES))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
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
        .doFinally {
          view.hideProgressBar()
          view.setupVenuesAdapter(venuesList.sortedBy { it.distance?.value })
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
            .child(Constants.FIREBASE_DATE)
            .child(auth.currentUser?.uid.let { it!! }), selectedDate.millis.toString())
        .doFinally { view.showChosenDateSnackBar(selectedDate, auth.uid!!) }
        .subscribe()
    disposables?.add(disposable)
  }

  override fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String) {
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
        .child(eventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_QUESTIONNAIRE)
        .child(Constants.FIREBASE_DATE)
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
}