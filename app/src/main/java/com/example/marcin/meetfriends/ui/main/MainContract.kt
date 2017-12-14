package com.example.marcin.meetfriends.ui.main

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-09.
 */
interface MainContract {

  interface View : MvpView {

    fun setUpActionBar(uri: String)

    fun startLoginActivity()

    fun showCreateEventDialog()

    fun showConfirmLogoutDialog()
  }

  interface Presenter : MvpPresenter {

    fun addNewEvent()

    fun tryLogout()

    fun logout()
  }
}