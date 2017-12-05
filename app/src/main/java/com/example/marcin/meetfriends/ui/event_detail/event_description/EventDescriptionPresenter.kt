package com.example.marcin.meetfriends.ui.event_detail.event_description

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.event_detail.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.GetParticipantsUseCase
import com.example.marcin.meetfriends.ui.friends.ParticipantsListParams
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-03.
 */
@ScreenScope
class EventDescriptionPresenter @Inject constructor(
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    eventInfoParams: EventBasicInfoParams,
    private val auth: FirebaseAuth
) : BasePresenter<EventDescriptionContract.View>(), EventDescriptionContract.Presenter {

  var participantsMutableList = mutableListOf<String>()
  val event = eventInfoParams.event
  var ifRemove = false

  override fun onViewCreated() {
    super.onViewCreated()
    view.showEventDescription(event.description.let { it!! })
    if (auth.currentUser?.uid == event.organizerId) {
      view.showInviteFriendsButton()
    }
  }

  override fun resume() {
    super.resume()
    loadParticipants()
  }

  private fun loadParticipants() {
    view.showNoParticipantsLayout()
    view.hideParticipantsProgressBar()
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(event.id).child(Constants.FIREBASE_PARTICIPANTS))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          view.showParticipantsProgressBar()
          val participantId = dataSnapshot.value.value.toString()
          participantsMutableList.add(participantId)
          if (participantsMutableList.size > participantsMutableList.distinct().size) {
            ifRemove = true
          }
          getEventParticipants(participantId)
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun getEventParticipants(participantId: String) {
    val disposable = RxFirebaseDatabase.observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_USERS))
        .subscribe({ user ->
          view.hideParticipantsProgressBar()
          if (user.key == participantId) {
            if (ifRemove) {
              view.manageEvent(RxFirebaseChildEvent(user.key, user.value, user.previousChildName, RxFirebaseChildEvent.EventType.REMOVED))
            } else {
              view.manageEvent(user)
            }
          }
        })
    disposables?.add(disposable)
  }

  override fun navigateToFriendsFragment(participants: MutableList<User>) {
    view.startFriendsActivity(
        EventIdParams(event.id.let { it!! }),
        ParticipantsListParams(ArrayList(participants.map { it.uid.let { it!! } }))
    )
  }
}