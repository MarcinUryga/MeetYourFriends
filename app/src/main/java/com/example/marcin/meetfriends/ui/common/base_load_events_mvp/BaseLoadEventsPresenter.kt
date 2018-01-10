package com.example.marcin.meetfriends.ui.common.base_load_events_mvp

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
        .observeChildEvent(databaseReferencePath())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val organizerIdPath = dataSnapshot.value.child(databaseEventOrganizerPath())
          if (isOrganizerEvent(organizerIdPath)) {
            manageEventItem(dataSnapshot)
          } else {
            loadEventsForParticipants(dataSnapshot)
          }
          if (isEmptyEventsList()) {
            view.showNoEventsView()
          } else {
            view.hideNoEventsLayout()
          }
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun loadEventsForParticipants(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(databaseEventParticipantsPath(dataSnapshot.key))
        .subscribe({ participantsIdDataSnapshot ->
          val participantsIdPath = participantsIdDataSnapshot.value
          if (participantsIdPath.value == auth.uid) {
            manageEventItem(dataSnapshot)
          }
        })
    disposables?.add(disposable)
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

  abstract fun manageEventItem(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>)

  abstract fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>): Boolean

  private fun databaseReferencePath() = firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)

  private fun databaseEventOrganizerPath() = Constants.FIREBASE_ORGANIZER_ID

  private fun isOrganizerEvent(organizerIdPath: DataSnapshot) = (organizerIdPath.getValue(String::class.java) == auth.uid)

  private fun databaseEventParticipantsPath(eventKey: String) = databaseReferencePath().child(eventKey).child(Constants.FIREBASE_PARTICIPANTS)

  private fun isEmptyEventsList() = view.getEventItemsSizeFromAdapter() == 0
}
