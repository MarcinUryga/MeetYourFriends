package com.example.marcin.meetfriends.ui.event_detail.event_description

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.event_detail.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.GetParticipantsUseCase
import com.example.marcin.meetfriends.ui.friends.ParticipantsListParams
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

  val participantsMutableList = mutableListOf<String>()
  val event = eventInfoParams.event

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

  fun loadParticipants() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(event.id).child(Constants.FIREBASE_PARTICIPANTS))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { /*view.showLoading()*/ }
        .subscribe({ dataSnapshot ->
          participantsMutableList.add(dataSnapshot.value.value.toString())
          Timber.d(dataSnapshot.toString())
          getEventParticipants(dataSnapshot.value.value.toString())
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  /* private fun getEventParticipants(participantId: String) {
     val disposable = getParticipantsUseCase.getParticipants(participantId)
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .doOnSubscribe { view.showParticipantsProgressBar() }
         .doFinally { view.hideParticipantsProgressBar() }
         .subscribe({ participant ->
           view.showParticipants(participant)
         })
     disposables?.add(disposable)
   }*/

  private fun getEventParticipants(participantId: String) {
    val disposable = RxFirebaseDatabase.observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_USERS))
        .subscribe({ user ->
          if (user.key == participantId) {
            view.manageEvent(user)
          }
        })
    disposables?.add(disposable)
  }

  /*private fun getEventParticipants(event: Event) {
    val disposable = getParticipantsUseCase.getParticipants(event.participants.map { it.value })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showParticipantsProgressBar() }
        .doFinally { view.hideParticipantsProgressBar() }
        .subscribe({ participants ->
          participantsIdList = ArrayList<String>(participants.map { it.uid })
          if (participants.isEmpty()) {
            view.showNoParticipantsLayout()
          } else {
            view.showParticipants(participants)
            if (auth.currentUser?.uid == event.organizerId) {
              view.showInviteFriendsButton()
            }
          }
        })
    disposables?.add(disposable)
  }*/

  override fun navigateToFriendsFragment() {
    view.startFriendsActivity(
        EventIdParams(event.id.let { it!! }),
        ParticipantsListParams(ArrayList(participantsMutableList))
    )
  }
}