package com.example.marcin.meetfriends.ui.search_venues

import com.example.marci.googlemaps.pojo.Place
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-12-24.
 */
interface SearchVenuesContract {

  interface View : MvpView {

    fun showVenues(venues: List<Place>)
  }

  interface Presenter : MvpPresenter
}