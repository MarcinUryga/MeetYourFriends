package com.example.marcin.meetfriends.ui.create_event

import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.models.Place
import com.example.marcin.meetfriends.models.nearby_place.Distance
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.GetPlaceDetailsUseCase
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.EventBasicInfo
import com.example.marcin.meetfriends.ui.search_venues.VenuesStorage
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-11-27.
 */
@ScreenScope
class CreateEventPresenter @Inject constructor(
    private val getPlaceDetailsUseCase: GetPlaceDetailsUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val venuesStorage: VenuesStorage,
    private val auth: FirebaseAuth
) : BasePresenter<CreateEventContract.View>(), CreateEventContract.Presenter {

  private val venuesList = mutableListOf<Place>()

  override fun onViewCreated() {
    super.onViewCreated()
    view.clearAdapter()
    venuesStorage.removeAll()
  }

  override fun resume() {
    super.resume()
    view.clearAdapter()
    if (venuesStorage.getAll().isNotEmpty()) {
      getVenues()
    }
//    mockVenues()
  }

  private fun getVenues() {
    venuesStorage.getAll().forEach {
      getPlaces(it)
    }
  }

  private fun mockVenues() {
    var i = 0
    venuesStorage.add("sadsadsad", "631")
    venuesStorage.add("asdsadsad", "123213")
    venuesStorage.add("3ewr5tt43", "345435")
    if (venuesStorage.getAll().isNotEmpty()) {
      venuesStorage.getAll().forEach {
        val venue = Place(
            id = it.key,
            name = "name $i",
            rating = i.toDouble(),
            location = Location(i.toDouble(), i.toDouble()),
            vicinity = i.toString(),
            distance = Distance("", it.value.toInt()),
            duration = null,
            photos = null,
            isAdded = true
        )
        view.addPlaceToAdapter(venue)
        venuesList.add(venue)
        i++
      }
    }
  }

  private fun getPlaces(it: Map.Entry<String, String>) {
    val disposable = getPlaceDetailsUseCase.get(it.key)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ place ->
          val venue = Place(
              id = place.result.placeId,
              name = place.result.name,
              rating = place.result.rating,
              location = place.result.geometry.location,
              vicinity = place.result.vicinity,
              distance = Distance("", it.value.toInt()),
              duration = null,
              photos = place.result.photos,
              isAdded = true
          )
          view.addPlaceToAdapter(venue)
          venuesList.add(venue)
        })
    disposables?.add(disposable)
  }

  override fun tryToCreateEvent() {
    if (view.validateEventName() && view.validateEventDescription()) {
      createEvent()
    }
  }

  override fun clickedEventIconButton() {
    view.startSelectEventIconDialog()
  }

  override fun searchVenues() {
    view.startSearchVenuesActivity()
  }

  override fun handleClickedActionButton(clickedActionButtonEvent: Observable<Place>) {
    val disposable = clickedActionButtonEvent.subscribe({ place ->
      venuesStorage.remove(place.id)
      view.removePlaceFromAdapter(place)
    })
    disposables?.add(disposable)
  }

  private fun createEvent() {
    val eventId = firebaseDatabase.reference.push().key
    val event = Event(
        id = eventId,
        iconId = view.getEventIconId(),
        organizerId = auth.uid,
        name = view.getEventName(),
        description = view.getEventDescription(),
        venues = venuesList.filter { venue ->
          venuesStorage.getAll().mapNotNull { it.key }.any { it == venue.id }
        }.map {
          FirebasePlace(
              id = it.id,
              name = it.name,
              rating = it.rating,
              latLng = "${it.location.lat},${it.location.lng}",
              vicinity = it.vicinity,
              photos = it.getPhotosUrl()
          )
        }.distinctBy { it.id }
    )
    val disposable = RxFirebaseDatabase
        .setValue(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(eventId), event)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {
          view.openEventDetailsActivity(EventBasicInfoParams(event = EventBasicInfo(
              id = event.id,
              iconId = event.iconId,
              organizerId = event.organizerId,
              name = event.name,
              description = event.description
          )))
        }
        .subscribe()
    disposables?.add(disposable)
  }
}