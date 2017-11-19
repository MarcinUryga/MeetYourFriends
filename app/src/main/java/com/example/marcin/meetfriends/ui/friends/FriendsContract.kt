package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by MARCIN on 2017-11-13.
 */
interface FriendsContract {

  interface View : MvpView {

    fun showInviteFriendsTitle()

    fun showLoading()

    fun showFriendsList(friendsList: List<User>)

    fun showToast(friendName: String)
  }

  interface Presenter : MvpPresenter {

    fun handleEvent(observable: Observable<User>)
  }
}