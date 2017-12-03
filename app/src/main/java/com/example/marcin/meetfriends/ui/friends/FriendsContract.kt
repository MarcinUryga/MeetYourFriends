package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.friends.viewmodel.Friend
import io.reactivex.Observable

/**
 * Created by MARCIN on 2017-11-13.
 */
interface FriendsContract {

  interface View : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showFriendsList(friendsList: List<Friend>)

    fun showInvitedFriendSnackBar(friend: Friend, eventId: String)
  }

  interface Presenter : MvpPresenter {

    fun handleInviteFriendEvent(observable: Observable<Friend>)

    fun removeParticipantFromEvent(friendId: String, eventId: String)
  }
}