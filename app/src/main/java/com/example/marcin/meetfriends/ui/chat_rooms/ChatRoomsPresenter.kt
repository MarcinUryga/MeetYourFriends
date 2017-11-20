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

  override fun onViewCreated() {
    super.onViewCreated()
    getEventsUseCase.get()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showLoading() }
        .doFinally { view.hideLoading() }
        .subscribe({ events ->
          if (events.isEmpty()) {
            view.showEmptyEvents()
          } else {
            view.showEvents(events)
          }
        })
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
}