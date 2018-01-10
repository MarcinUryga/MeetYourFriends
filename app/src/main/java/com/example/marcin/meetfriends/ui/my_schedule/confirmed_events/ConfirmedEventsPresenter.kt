package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.ui.common.params.EventIdParams
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 * */
@ScreenScope
class ConfirmedEventsPresenter @Inject constructor(
    auth: FirebaseAuth,
    firebaseDatabase: FirebaseDatabase
) : BaseLoadEventsPresenter<ConfirmedEventsContract.View>(auth, firebaseDatabase), ConfirmedEventsContract.Presenter {

  override fun resume() {
    super.resume()
    loadEvents()
  }

  override fun manageEventItem(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    if (isFinishedVoting(dataSnapshot) || dataSnapshot.eventType == RxFirebaseChildEvent.EventType.REMOVED) {
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
  override fun handleChosenEvent(clickEvent: Observable<Event>) {
    val disposable = clickEvent.subscribe({ event ->
      view.startConfirmedEventActivity(EventIdParams(eventId = event.id.let { it!! }))
    })
    disposables?.add(disposable)
  }

  override fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) = !dataSnapshot.value.getValue(Event::class.java)?.finishedVoting.let { it!! }
}
