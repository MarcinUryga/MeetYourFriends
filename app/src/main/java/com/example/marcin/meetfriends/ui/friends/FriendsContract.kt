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

    fun showLoading()

    fun hideLoading()

    fun showFriendsList(friendsList: List<User>)

    fun showInvitedFriendSnackBar(friend: User, eventId: String)
  }

  interface Presenter : MvpPresenter {

    fun handleInviteFriendEvent(observable: Observable<User>)

    fun removeParticipantFromEvent(friendId: String, eventId: String)
  }
}