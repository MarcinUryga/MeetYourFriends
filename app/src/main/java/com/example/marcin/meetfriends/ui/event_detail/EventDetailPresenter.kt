package com.example.marcin.meetfriends.ui.event_detail

import android.content.res.Resources
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventIdParams
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
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val eventInfoParams: EventBasicInfoParams,
    private val auth: FirebaseAuth
) : BasePresenter<EventDetailContract.View>(), EventDetailContract.Presenter {

  val eventId = eventInfoParams.event.id.let { it!! }
  val organizerId = eventInfoParams.event.organizerId.let { it!! }

  override fun resume() {
    super.resume()
    view.setUpEventDescriptionFragment(eventInfoParams)
    loadEvent()
  }

  private fun loadEvent() {
    view.setUpToolbarEventName(eventInfoParams.event.name!!)
    getEventOrganizer(organizerId)
    //          getEventParticipants(event)
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

  override fun navigateToEventChat() {
    view.startEventChatActivity(EventIdParams(eventId))
  }

  override fun navigateToEventQuestionnaire() {
    view.startEventVoteActivity(EventIdParams(eventId))
  }

  override fun onDeleteClicked(resources: Resources) {
    if (auth.currentUser?.uid == organizerId) {
      view.openDeleteButtonDialog(resources.getString(R.string.do_you_really_want_to_delete_this_event))
    } else {
      view.openDeleteButtonDialog(resources.getString(R.string.do_you_really_want_to_leave_this_event))
    }
  }

  override fun deleteEvent() {
    view.startMainActivity()
    if (auth.currentUser?.uid == organizerId) {
      firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(eventId).removeValue()
    } else {
      firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
          .child(eventInfoParams.event.id.let { it!! })
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