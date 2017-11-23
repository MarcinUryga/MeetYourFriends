package com.example.marcin.meetfriends.ui.chat_rooms

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-11-20.
 */
@ScreenScope
class ChatRoomsPresenter @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val getEventsUseCase: GetEventsUseCase,
    private val auth: FirebaseAuth,
    sharedPreferences: SharedPreferences
) : BasePresenter<ChatRoomsContract.View>(), ChatRoomsContract.Presenter {

  private val sharedPref = SharedPref(sharedPreferences)
  val events = mutableListOf<Event>()

  override fun resume() {
    super.resume()
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
        .subscribeOn(Schedulers.io())
        .doAfterNext {
          if (events.isNotEmpty()) {
            view.hideEmptyEvents()
          }
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val organizerIdPath = dataSnapshot.value.child(Constants.FIREBASE_ORGANIZER_ID)
          val participantsIdPath = dataSnapshot.value.child(Constants.FIREBASE_USERS)
          if ((organizerIdPath.getValue(String::class.java) == auth.uid)) {
            addEventToEventList(events, dataSnapshot)
          }
          if (participantsIdPath.exists()) {
            participantsIdPath.children.forEach {
              if (it.key == auth.uid) {
                addEventToEventList(events, dataSnapshot)
              }
            }
          }
          view.hideEmptyEvents()
          view.showEvents(events)
          if (!organizerIdPath.exists() || events.isEmpty()) {
            view.showEmptyEvents()
          }
        })
    disposables?.add(disposable)
  }

  private fun addEventToEventList(events: MutableList<Event>, dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val event = dataSnapshot.value.getValue(Event::class.java).let { it!! }
    val index = events.filter { it.id == event.id }.lastIndex
    if (dataSnapshot.eventType == RxFirebaseChildEvent.EventType.ADDED) {
      if (event !in events) {
        events.add(event)
      }
    } else if (dataSnapshot.eventType == RxFirebaseChildEvent.EventType.CHANGED) {
      events[index] = event
    } else if (dataSnapshot.eventType == RxFirebaseChildEvent.EventType.REMOVED) {
      events.remove(event)
    }
  }

  override fun addNewEvent() {
    view.showCreateEventDialog()
  }

  override fun createEvent(eventName: String) {
    val eventId = firebaseDatabase.reference.push().key
    val event = Event(
        id = eventId,
        organizerId = auth.uid,
        name = eventName
    )
    val disposable = RxFirebaseDatabase
        .setValue(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(eventId), event)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {
          view.showCreatedEventSnackBar(eventId)
          sharedPref.saveChosenEvent(eventId)
        }
        .subscribe()
    disposables?.add(disposable)
  }

  override fun removeEvent(eventId: String) {
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(eventId).removeValue()
  }

  override fun handleChosenChatRoomdEvent(eventChatRoom: Observable<Event>) {
    val disposable = eventChatRoom.subscribe({ event ->
      view.startChatRoomActivity(event)
    })
    disposables?.add(disposable)
  }
}