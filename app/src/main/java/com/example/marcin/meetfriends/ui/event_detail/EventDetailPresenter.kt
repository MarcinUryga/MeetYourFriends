package com.example.marcin.meetfriends.ui.event_detail

import android.content.res.Resources
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.friends.ParticipantsListParams
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-28.
 */
@ScreenScope
class EventDetailPresenter @Inject constructor(
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val eventIdParams: EventIdParams,
    private val auth: FirebaseAuth
) : BasePresenter<EventDetailContract.View>(), EventDetailContract.Presenter {

  lateinit var organizerId: String
  var participantsIdList: ArrayList<String>? = null

  override fun resume() {
    super.resume()
    loadEvent()
  }

  private fun loadEvent() {
    val disposable = getEventDetailsUseCase.get(eventIdParams.eventId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showEventDescriptionProgressBar() }
        .doFinally { view.hideEventDescriptionProgressBar() }
        .subscribe({ event ->
          organizerId = event.organizerId.let { it!! }
          view.showEventDetails(event)
          getEventOrganizer(event.organizerId.let { it!! })
          getEventParticipants(event)
        })
    disposables?.add(disposable)
  }

  private fun getEventOrganizer(organizerId: String) {
    val disposable = getParticipantsUseCase.getOrganizer(organizerId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ organizer ->
          view.setUpOrganizerData(organizer)
        })
    disposables?.add(disposable)
  }

  private fun getEventParticipants(event: Event) {
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
            if (auth.currentUser?.uid == organizerId) {
              view.showInviteFriendsButton()
            }
          }
        })
    disposables?.add(disposable)
  }

  override fun navigateToFriendsFragment() {
    if (participantsIdList != null) {
      view.startFriendsActivity(EventIdParams(eventIdParams.eventId), ParticipantsListParams(participantsIdList!!))
    }
  }

  override fun navigateToEventChat() {
    view.startEventChatActivity(EventIdParams(eventIdParams.eventId))
  }

  override fun navigateToEventQuestionnaire() {
    view.startEventVoteActivity(EventIdParams(eventIdParams.eventId))
  }

  override fun onDeleteClicked(resources: Resources) {
    if (organizerId.isNotEmpty()) {
      if (auth.currentUser?.uid == organizerId) {
        view.openDeleteButtonDialog(resources.getString(R.string.do_you_really_want_to_delete_this_event))
      } else {
        view.openDeleteButtonDialog(resources.getString(R.string.do_you_really_want_to_leave_this_event))
      }
    }
  }

  override fun deleteEvent() {
    view.startMainActivity()
    if (organizerId.isNotEmpty()) {
      if (auth.currentUser?.uid == organizerId) {
        firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(eventIdParams.eventId).removeValue()
      } else {
        firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
            .child(eventIdParams.eventId)
            .child(Constants.FIREBASE_PARTICIPANTS)
            .orderByValue()
            .equalTo(auth.currentUser?.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
              override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.ref.child(dataSnapshot.children.firstOrNull { it.value == auth.currentUser?.uid }?.key.toString()).removeValue()
              }

              override fun onCancelled(p0: DatabaseError?) {
                Timber.d("Canncelled remove participant with id ${auth.currentUser?.uid}")
              }
            })
      }
    }
  }
}