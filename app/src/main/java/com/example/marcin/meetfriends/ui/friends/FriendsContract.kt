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

    fun showCreateEventDialog()

    fun showCreatedEventSnackBar(eventId: String)

    fun showInvitedFriendSnackBar(friend: User, eventId: String)
  }

  interface Presenter : MvpPresenter {

    fun handleEvent(observable: Observable<User>)

    fun createEvent(eventName: String)

    fun removeEvent(eventId: String)

    fun removeFriendFromEvent(friendId: String, eventId: String)
  }
}