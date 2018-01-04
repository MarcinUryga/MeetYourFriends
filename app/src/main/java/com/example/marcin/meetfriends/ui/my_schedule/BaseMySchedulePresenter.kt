package com.example.marcin.meetfriends.ui.my_schedule

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
abstract class BaseMySchedulePresenter<T : BaseMyScheduleContract.View>(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : BasePresenter<T>(), BaseMyScheduleContract.Presenter {

  protected fun loadEvents() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(databaseReferencePath())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val organizerIdPath = dataSnapshot.value.child(Constants.FIREBASE_ORGANIZER_ID)
          if ((organizerIdPath.getValue(String::class.java) == auth.uid)) {
            manageEventItem(dataSnapshot)
          } else {
            RxFirebaseDatabase.observeChildEvent(databaseReferencePath().child(dataSnapshot.key).child(Constants.FIREBASE_PARTICIPANTS))
                .subscribe({ participantsIdDataSnapshot ->
                  val participantsIdPath = participantsIdDataSnapshot.value
                  if (participantsIdPath.value == auth.uid) {
                    manageEventItem(dataSnapshot)
                  }
                })
          }
          if (view.getEventItemsSizeFromAdapter() == 0) {
            view.showNoEventsView()
          } else {
            view.hideNoEventsLayout()
          }
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun manageEventItem(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    if (isFinishedVoting(dataSnapshot)) {
      removeEvent(dataSnapshot)
    } else if (!isFinishedVoting(dataSnapshot)) {
      addEvent(dataSnapshot)
    }
    if (view.getEventItemsSizeFromAdapter() == 0) {
      view.showNoEventsView()
    } else {
      view.hideNoEventsLayout()
    }
    view.hideLoadingProgressBar()
  }

  private fun databaseReferencePath() = firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)

  abstract fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>): Boolean

  private fun removeEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(
        RxFirebaseChildEvent(
            dataSnapshot.key,
            dataSnapshot.value,
            dataSnapshot.previousChildName,
            RxFirebaseChildEvent.EventType.REMOVED
        )
    )
  }

  private fun addEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(RxFirebaseChildEvent(
        dataSnapshot.key,
        dataSnapshot.value,
        dataSnapshot.previousChildName,
        RxFirebaseChildEvent.EventType.ADDED
    ))
  }
}
