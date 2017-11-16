package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by MARCIN on 2017-11-13.
 */
interface FriendsContract {

  interface View : MvpView {

    fun showFriendsList(friendsList: List<User>)
  }

  interface Presenter : MvpPresenter
}