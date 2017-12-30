package com.example.marcin.meetfriends.ui.search_venues

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.PlaceIdParams
import com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place
import io.reactivex.Observable

/**
 * Created by marci on 2017-12-24.
 */
interface SearchVenuesContract {

  interface View : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showEmptyVenuesList(type: String)

    fun showVenues(venues: List<Place>)

    fun buildAlertMessageNoGps()

    fun startPlaceDetailsActivity(params: PlaceIdParams)

    fun addedPlace(place: Place)
  }

  interface Presenter : MvpPresenter {

    fun getNearbyPlaces(type: String)

    fun handleChosenPlace(clickEvent: Observable<Place>)

    fun handleClickedActionButton(clickedActionButtonEvent: Observable<Place>)
  }
}