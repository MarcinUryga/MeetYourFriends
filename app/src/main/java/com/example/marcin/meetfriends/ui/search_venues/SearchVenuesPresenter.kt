package com.example.marcin.meetfriends.ui.search_venues

import com.example.marci.googlemaps.pojo.Location
import com.example.marci.googlemaps.pojo.Place
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-24.
 */
@ScreenScope
class SearchVenuesPresenter @Inject constructor(
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase
) : BasePresenter<SearchVenuesContract.View>(), SearchVenuesContract.Presenter {

  lateinit var nearbyPlaces: MutableList<com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place>

  override fun getNearbyPlaces(type: String, location: Location) {
    val disposable = getNearbyPlacesUseCase.getPlaces(type, type, "49.767151,20.4531756")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ venues ->
          nearbyPlaces = mutableListOf()
          venues.places.forEach {
            getPlaceDistanceMatrix("49.767151,20.4531756", it)
          }
          if (nearbyPlaces.isEmpty()) {
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