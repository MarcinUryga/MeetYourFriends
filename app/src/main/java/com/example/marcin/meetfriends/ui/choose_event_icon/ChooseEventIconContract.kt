package com.example.marcin.meetfriends.ui.choose_event_icon

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-12-15.
 */
interface ChooseEventIconContract {

  interface View : MvpView {

    fun dismiss()
  }

  interface Presenter : MvpPresenter
}