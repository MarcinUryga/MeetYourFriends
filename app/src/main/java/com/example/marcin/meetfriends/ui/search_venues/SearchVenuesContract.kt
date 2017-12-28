package com.example.marcin.meetfriends.ui.search_venues

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place

/**
 * Created by marci on 2017-12-24.
 */
interface SearchVenuesContract {

  interface View : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showVenues(venues: List<Place>)
  }

  interface Presenter : MvpPresenter
}