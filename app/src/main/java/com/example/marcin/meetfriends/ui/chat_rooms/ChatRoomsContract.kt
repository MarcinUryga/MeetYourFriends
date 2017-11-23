package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable

/**
 * Created by marci on 2017-11-20.
 */
interface ChatRoomsContract {

  interface View : MvpView {

    fun showCreateEventDialog()

    fun showCreatedEventSnackBar(eventId: String)

    fun showEmptyEvents()

    fun hideEmptyEvents()

    fun hideRefresh()

    fun startChatRoomActivity(event: Event)

    fun manageEvent(post: RxFirebaseChildEvent<DataSnapshot>)
  }

  interface Presenter : MvpPresenter {

    fun addNewEvent()

    fun createEvent(eventName: String)

    fun removeEvent(eventId: String)

    fun onRefresh()

    fun handleChosenChatRoomdEvent(eventChatRoom: Observable<Event>)
  }
}