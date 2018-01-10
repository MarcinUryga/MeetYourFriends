package com.example.marcin.meetfriends.ui.common.base_load_events_mvp

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by marci on 2018-01-03.
 */
abstract class BaseLoadEventsPresenter<T : BaseLoadEventsContract.View>(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : BasePresenter<T>(), BaseLoadEventsContract.Presenter {

  protected fun loadEvents() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(databaseEventsPath())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val eventData = dataSnapshot.value.getValue(Event::class.java)
          if (isOrganizerEvent(eventData?.organizerId.let { it!! })) {
            manageEventItem(dataSnapshot)
          } else if (isParticipantEvent(eventData)) {
            manageEventItem(dataSnapshot)
          }
          manageEmptyEventsLayout()
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun isParticipantEvent(eventData: Event?) =
      eventData?.participants?.any { it.value == auth.currentUser?.uid }!!

  private fun manageEmptyEventsLayout() {
    if (isEmptyEventsList()) {
      view.showNoEventsView()
    } else {
      view.hideNoEventsLayout()
    }
  }

  protected fun removeEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(
        RxFirebaseChildEvent(
            dataSnapshot.key,
            dataSnapshot.value,
            dataSnapshot.previousChildName,
            RxFirebaseChildEvent.EventType.REMOVED
        )
    )
  }

  protected fun addEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(RxFirebaseChildEvent(
        dataSnapshot.key,
        dataSnapshot.value,
        dataSnapshot.previousChildName,
        RxFirebaseChildEvent.EventType.ADDED
    ))
  }

  protected fun changeEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(RxFirebaseChildEvent(
        dataSnapshot.key,
        dataSnapshot.value,
        dataSnapshot.previousChildName,
        RxFirebaseChildEvent.EventType.CHANGED
    ))
  }

  abstract fun manageEventItem(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>)

  abstract fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>): Boolean

  private fun databaseEventsPath() = firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)

  private fun databaseEventOrganizerPath() = Constants.FIREBASE_ORGANIZER_ID

  private fun isOrganizerEvent(organizerId: String) = organizerId == auth.uid

  private fun databaseEventParticipantsPath(eventKey: String) = databaseEventsPath().child(eventKey).child(Constants.FIREBASE_PARTICIPANTS)

  private fun isEmptyEventsList() = view.getEventItemsSizeFromAdapter() == 0
}
