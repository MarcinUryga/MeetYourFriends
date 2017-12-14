package com.example.marcin.meetfriends.ui.create_event

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-27.
 */
interface CreateEventContract {

  interface View : MvpView {

    fun validateEventName(): Boolean

    fun validateEventDescription(): Boolean

    fun getEventName(): String

    fun getEventDescription(): String

    fun dismissDialogFragment()
  }

  interface Presenter : MvpPresenter {

    fun tryToCreateEvent()
  }
}