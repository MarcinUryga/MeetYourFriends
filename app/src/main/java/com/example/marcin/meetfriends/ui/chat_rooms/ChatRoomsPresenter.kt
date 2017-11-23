package com.example.marcin.meetfriends.ui.chat_rooms

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    private val auth: FirebaseAuth,
    sharedPreferences: SharedPreferences
) : BasePresenter<ChatRoomsContract.View>(), ChatRoomsContract.Presenter {

  private val sharedPref = SharedPref(sharedPreferences)

  override fun resume() {
    super.resume()
    loadChatRooms()
    loadInvitations()
  }

  private fun loadChatRooms() {
    /* view.hideRefresh()
     val disposable = RxFirebaseDatabase
         .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe({ dataSnapshot ->
           val organizerIdPath = dataSnapshot.value.child(Constants.FIREBASE_ORGANIZER_ID)
           if ((organizerIdPath.getValue(String::class.java) == auth.uid)) {
             view.manageEvent(dataSnapshot)
             view.hideEmptyEvents()
           }
         })
     disposables?.add(disposable)*/
  }

  fun loadInvitations() {
    view.hideRefresh()
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val participantsIdPath = dataSnapshot.value.child(Constants.FIREBASE_USERS)
          participantsIdPath.children.forEach {
            if (it.value == auth.uid) {
              view.manageEvent(dataSnapshot)
              view.hideEmptyEvents()
            }
          }
        })
    disposables?.add(disposable)
  }

  override fun onRefresh() {
    loadChatRooms()
    loadInvitations()
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