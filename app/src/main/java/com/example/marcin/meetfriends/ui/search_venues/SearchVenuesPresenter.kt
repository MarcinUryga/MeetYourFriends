package com.example.marcin.meetfriends.ui.search_venues

import android.app.Activity
import android.location.LocationManager
import com.example.marci.googlemaps.pojo.Location
import com.example.marci.googlemaps.pojo.Place
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.GetNearbyPlacesUseCase
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
    private val getDeviceLocationUseCase: GetDeviceLocationUseCase
) : BasePresenter<SearchVenuesContract.View>(), SearchVenuesContract.Presenter {

  private var currentLocation = Location(0.0, 0.0)
  lateinit var nearbyPlaces: MutableList<com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place>

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
        .doFinally { view.hideProgressBar() }
        .subscribe({ venues ->
          nearbyPlaces = mutableListOf()
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

  private fun getPlaceDistanceMatrix(origins: String, place: Place) {
    val disposable = getNearbyPlacesUseCase.getDistanceMatrix(origins, "${place.geometry.location.lat},${place.geometry.location.lng}")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
        .doFinally { view.hideProgressBar() }
        .subscribe({ distanceMatrix ->
          nearbyPlaces.add(com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place(
              id = place.id,
              name = place.name,
              rating = place.rating,
              location = place.geometry.location,
              vicinity = place.vicinity,
              distance = distanceMatrix.rows.first().elements.first().distance,
              duration = distanceMatrix.rows.first().elements.first().duration,
              photos = place.photos
          ))
          view.showVenues(nearbyPlaces)
        })
    disposables?.add(disposable)
  }
}