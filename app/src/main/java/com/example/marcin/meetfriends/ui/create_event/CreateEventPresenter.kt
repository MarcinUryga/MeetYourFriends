package com.example.marcin.meetfriends.ui.create_event

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.utils.Constants
import com.example.marcin.meetfriends.utils.Constants.FIREBASE_EVENTS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-11-27.
 */
@ScreenScope
class CreateEventPresenter @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : BasePresenter<CreateEventContract.View>(), CreateEventContract.Presenter {

  override fun tryToCreateEvent() {
    if (view.validateEventName() && view.validateEventDescription()) {
      createEvent(view.getEventName(), view.getEventDescription())
    }
  }

  private fun createEvent(eventName: String, eventDescription: String) {
    val eventId = firebaseDatabase.reference.push().key
    val event = Event(
        id = eventId,
        organizerId = auth.uid,
        name = eventName,
        description = eventDescription
    )
    val disposable = RxFirebaseDatabase
        .setValue(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(eventId), event)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally { view.showCreatedEventSnackBar(eventId) }
        .subscribe()
    disposables?.add(disposable)
  }

  override fun removeEvent(eventId: String) {
    firebaseDatabase.reference.child(FIREBASE_EVENTS).child(eventId).removeValue()
  }
}