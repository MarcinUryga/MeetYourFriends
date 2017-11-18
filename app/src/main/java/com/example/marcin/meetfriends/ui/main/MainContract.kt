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

    fun confirmLogoutDialog()
  }

  interface Presenter : MvpPresenter {

    fun tryLogout()

    fun logout()
  }
}