package com.example.marcin.meetfriends.ui.create_event

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.EventBasicInfo
import com.example.marcin.meetfriends.utils.Constants
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
      createEvent()
    }
  }

  override fun clickedEventIconButton() {
    view.startSelectEventIconDialog()
  }

  override fun searchVenues() {
    view.startSearchVenuesActivity()
  }

  private fun createEvent() {
    val eventId = firebaseDatabase.reference.push().key
    val event = Event(
        id = eventId,
        iconId = view.getEventIconId(),
        organizerId = auth.uid,
        name = view.getEventName(),
        description = view.getEventDescription()
    )
    val disposable = RxFirebaseDatabase
        .setValue(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(eventId), event)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {
          view.openEventDetailsActivity(EventBasicInfoParams(event = EventBasicInfo(
              id = event.id,
              iconId = event.iconId,
              organizerId = event.organizerId,
              name = event.name,
              description = event.description
          )))
        }
        .subscribe()
    disposables?.add(disposable)
  }
}