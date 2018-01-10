package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsPresenter
import com.example.marcin.meetfriends.ui.planned_event_detail.viewmodel.EventBasicInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by marci on 2017-11-20.
 */
@ScreenScope
class ChatRoomsPresenter @Inject constructor(
    auth: FirebaseAuth,
    firebaseDatabase: FirebaseDatabase
) : BaseLoadEventsPresenter<ChatRoomsContract.View>(auth, firebaseDatabase), ChatRoomsContract.Presenter {

  override fun resume() {
    super.resume()
    loadEvents()
  }

  override fun manageEventItem(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    addEvent(dataSnapshot)
    if (view.getEventItemsSizeFromAdapter() == 0) {
      view.showNoEventsView()
    } else {
      view.hideNoEventsLayout()
    }
    view.hideLoadingProgressBar()
  }

  override fun handleChosenEvent(clickEvent: Observable<Event>) {
    val disposable = clickEvent.subscribe({ event ->
      view.startChatRoomActivity(EventBasicInfoParams(
          event = EventBasicInfo(
              id = event.id,
              organizerId = event.organizerId,
              name = event.name,
              description = event.description
          )))
    })
    disposables?.add(disposable)
  }

  override fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}