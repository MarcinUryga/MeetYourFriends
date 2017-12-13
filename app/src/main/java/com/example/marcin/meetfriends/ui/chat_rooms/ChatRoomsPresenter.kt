package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.EventBasicInfo
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-20.
 */
@ScreenScope
class ChatRoomsPresenter @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : BasePresenter<ChatRoomsContract.View>(), ChatRoomsContract.Presenter {

  private var loading = false

  override fun resume() {
    super.resume()
    loadChatRooms()
  }

  private fun loadChatRooms() {
    view.hideRefresh()
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
          view.showLoading()
          loading = true
        }
        .subscribe({ dataSnapshot ->
          val organizerIdPath = dataSnapshot.value.child(Constants.FIREBASE_ORGANIZER_ID)
          if ((organizerIdPath.getValue(String::class.java) == auth.uid)) {
            addEvent(dataSnapshot)
          } else {
            view.showEmptyEvents()
            view.hideLoading()
            findParticipants(dataSnapshot)
          }
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun findParticipants(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = RxFirebaseDatabase.observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(dataSnapshot.key).child(Constants.FIREBASE_PARTICIPANTS))
        .subscribe({ participantsIdDataSnapshot ->
          val participantsIdPath = participantsIdDataSnapshot.value
          if (participantsIdPath.value == auth.uid) {
            addEvent(dataSnapshot)
          }
        })
    disposables?.add(disposable)
  }

  private fun addEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(dataSnapshot)
    if (loading) {
      view.hideLoading()
    }
    view.hideEmptyEvents()
  }

  override fun onRefresh() {
    loadChatRooms()
  }

  override fun handleChosenChatRoomdEvent(eventChatRoom: Observable<Event>) {
    val disposable = eventChatRoom.subscribe({ event ->
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
}