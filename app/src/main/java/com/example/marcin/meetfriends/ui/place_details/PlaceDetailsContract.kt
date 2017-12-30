package com.example.marcin.meetfriends.ui.place_details

import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.place_details.viewmodel.PlaceDetails

/**
 * Created by marci on 2017-12-29.
 */
interface PlaceDetailsContract {

  interface View : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showPlaceDetails(placeDetails: PlaceDetails)

    fun startGoogleMaps(placeLocation: Location)
  }

  interface Presenter : MvpPresenter {

    fun clickedOpenGoogleMapsButton()
  }
}