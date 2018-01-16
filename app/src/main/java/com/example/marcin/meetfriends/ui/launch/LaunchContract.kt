package com.example.marcin.meetfriends.ui.launch

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-11.
 */
interface LaunchContract {

  interface View : MvpView {

    fun startLoginAcivity()

    fun startMainActivity()
  }

  interface Presenter : MvpPresenter
}