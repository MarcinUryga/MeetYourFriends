package com.example.marcin.meetfriends.ui.my_schedule.planned_events

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.my_schedule.BaseMySchedulePresenter
import com.example.marcin.meetfriends.ui.planned_event_detail.viewmodel.EventBasicInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by marci on 2018-01-04.
 */
@ScreenScope
class PlannedEventsPresenter @Inject constructor(
    firebaseDatabase: FirebaseDatabase,
    auth: FirebaseAuth
) : BaseMySchedulePresenter<PlannedEventsContract.View>(auth, firebaseDatabase), PlannedEventsContract.Presenter {

  override fun resume() {
    super.resume()
    loadEvents()
  }

  override fun handleChosenEvent(clickEvent: Observable<Event>) {
    val disposable = clickEvent.subscribe({ event ->
      view.startEventDetailActivity(EventBasicInfoParams(
          event = EventBasicInfo(
              id = event.id,
              iconId = event.iconId,
              organizerId = event.organizerId,
              name = event.name,
              description = event.description
          ))
      )
    })
    disposables?.add(disposable)
  }

  override fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) = dataSnapshot.value.getValue(Event::class.java)?.finishedVoting.let { it!! }
}