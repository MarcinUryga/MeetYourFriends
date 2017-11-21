package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by marci on 2017-11-20.
 */
interface ChatRoomsContract {

  interface View : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showCreateEventDialog()

    fun showCreatedEventSnackBar(eventId: String)

    fun showEmptyEvents()

    fun showEvents(events: List<Event>)

    fun startChatRoomActivity(event: Event)
  }

  interface Presenter : MvpPresenter {

    fun addNewEvent()

    fun createEvent(eventName: String)

    fun removeEvent(eventId: String)

    fun handleChosenChatRoomdEvent(eventChatRoom: Observable<Event>)
  }
}