package com.example.marcin.meetfriends.ui.create_event

import com.example.marcin.meetfriends.models.Place
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import io.reactivex.Observable

/**
 * Created by marci on 2017-11-27.
 */
interface CreateEventContract {

  interface View : MvpView {

    fun validateEventName(): Boolean

    fun validateEventDescription(): Boolean

    fun getEventName(): String

    fun getEventDescription(): String

    fun getEventItemsSizeFromAdapter(): Int

    fun openEventDetailsActivity(eventBasicInfoParams: EventBasicInfoParams)

    fun startSelectEventIconDialog()

    fun getEventIconId(): String?

    fun startSearchVenuesActivity()

    fun addPlaceToAdapter(venue: Place)

    fun clearAdapter()

    fun removePlaceFromAdapter(place: Place)

    fun showVenuesProgressBar()

    fun hideVenuesProgressBar()

    fun showNoPlacsToast()

    fun showProgressDialog()

    fun hideProgressDialog()
  }

  interface Presenter : MvpPresenter {

    fun tryToCreateEvent()

    fun clickedEventIconButton()

    fun searchVenues()

    fun handleClickedActionButton(clickedActionButtonEvent: Observable<Place>)
  }
}