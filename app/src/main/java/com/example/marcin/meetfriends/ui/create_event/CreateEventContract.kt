package com.example.marcin.meetfriends.ui.create_event

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams

/**
 * Created by marci on 2017-11-27.
 */
interface CreateEventContract {

  interface View : MvpView {

    fun validateEventName(): Boolean

    fun validateEventDescription(): Boolean

    fun getEventName(): String

    fun getEventDescription(): String

    fun openEventDetailsActivity(eventBasicInfoParams: EventBasicInfoParams)

    fun startSelectEventIconDialog()

    fun getEventIconId(): String?

    fun startSearchVenuesActivity()
  }

  interface Presenter : MvpPresenter {

    fun tryToCreateEvent()

    fun clickedEventIconButton()

    fun searchVenues()
  }
}