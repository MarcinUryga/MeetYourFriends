package com.example.marcin.meetfriends.ui.search_venues

import android.app.Activity
import android.location.LocationManager
import com.example.marci.googlemaps.pojo.Location
import com.example.marci.googlemaps.pojo.Place
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.use_cases.GetDeviceLocationUseCase
import com.example.marcin.meetfriends.ui.common.use_cases.GetNearbyPlacesUseCase
import com.example.marcin.meetfriends.ui.common.params.PlaceIdParams
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-24.
 */
@ScreenScope
class SearchVenuesPresenter @Inject constructor(
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val locationManager: LocationManager,
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase,
    private val venuesStorage: VenuesStorage
) : BasePresenter<SearchVenuesContract.View>(), SearchVenuesContract.Presenter {

  private var currentLocation = Location(0.0, 0.0)

  override fun resume() {
    super.resume()
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      view.buildAlertMessageNoGps()
    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      currentLocation = getDeviceLocationUseCase.get(view as Activity)
    }
  }

  override fun getNearbyPlaces(type: String) {
    val disposable = getNearbyPlacesUseCase.getPlaces(type, type, "${currentLocation.lat},${currentLocation.lng}")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
        .subscribe({ venues ->
          if (venues.places.isNotEmpty()) {
            venues.places.forEach {
              getPlaceDistanceMatrix("${currentLocation.lat},${currentLocation.lng}", it)
            }
          } else {
            view.showEmptyVenuesList(type)
          }
        }, { error ->
          Timber.e(SearchVenuesActivity::class.java.simpleName, error.printStackTrace())
        })
    disposables?.add(disposable)
  }

  override fun handleChosenPlace(clickEvent: Observable<com.example.marcin.meetfriends.models.Place>) {
    val disposable = clickEvent.subscribe({ place ->
      view.startPlaceDetailsActivity(PlaceIdParams(placeId = place.id))
    })
    disposables?.add(disposable)
  }

  override fun handleClickedActionButton(clickedActionButtonEvent: Observable<com.example.marcin.meetfriends.models.Place>) {
    val disposable = clickedActionButtonEvent.subscribe({ place ->
      if (place.isAdded) {
        venuesStorage.remove(place.id)
        view.showToast("Removed: ${place.name}")
      } else {
        venuesStorage.add(place.id, place.distance?.value.toString())
        view.showToast("Added: ${place.name}")
      }
    })
    disposables?.add(disposable)
  }

  private fun getPlaceDistanceMatrix(origins: String, place: Place) {
    val disposable = getNearbyPlacesUseCase.getDistanceMatrix(origins, "${place.geometry.location.lat},${place.geometry.location.lng}")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally { view.hideProgressBar() }
        .subscribe({ distanceMatrix ->
          val venue = com.example.marcin.meetfriends.models.Place(
              id = place.placeId,
              name = place.name,
              rating = place.rating,
              location = place.geometry.location,
              vicinity = place.vicinity,
              distance = distanceMatrix.rows.first().elements.first().distance,
              duration = distanceMatrix.rows.first().elements.first().duration,
              photos = place.photos,
              placeIcon = place.icon,
              isAdded = venuesStorage.getAll().mapNotNull { it.key }.any { it == place.placeId }
          )
          view.addPlaceToAdapter(venue)
        })
    disposables?.add(disposable)
  }
}