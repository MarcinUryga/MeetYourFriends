package com.example.marcin.meetfriends.ui.menu_navigation

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-15.
 */
interface MainNavigationContract {

  interface View : MvpView {

    fun setNavigationHeader(user: User)
  }

  interface Presenter : MvpPresenter
}