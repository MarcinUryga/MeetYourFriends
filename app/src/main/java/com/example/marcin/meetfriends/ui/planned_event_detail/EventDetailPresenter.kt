package com.example.marcin.meetfriends.ui.planned_event_detail

import android.content.res.Resources
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Questionnaire
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.common.GetFilledQuestionnairesUseCase
import com.example.marcin.meetfriends.ui.common.GetParticipantsUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-28.
 */
@ScreenScope
class EventDetailPresenter @Inject constructor(
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val eventInfoParams: EventBasicInfoParams,
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : BasePresenter<EventDetailContract.View>(), EventDetailContract.Presenter {

  val eventId = eventInfoParams.event.id.let { it!! }
  val organizerId = eventInfoParams.event.organizerId.let { it!! }

  override fun onViewCreated() {
    super.onViewCreated()
    if (organizerId != auth.currentUser?.uid) {
      view.hideFinishVotingButton()
      val disposable = RxFirebaseDatabase.observeChildEvent(
          firebaseDatabase.reference
              .child(Constants.FIREBASE_EVENTS)
              .child(eventId))
          .subscribe({ event ->
            if (event.key == Constants.FIREBASE_FINISHED_VOTING && event.value.getValue(Boolean::class.java) == true) {
              view.startConfirmedEventActivity(EventIdParams(eventId))
            }
          })
      disposables?.add(disposable)
    }
  }

  override fun resume() {
    super.resume()
    loadEvent()
  }

  private fun loadEvent() {
    view.setUpToolbarEventName(eventInfoParams.event.name.let { it!! })
    view.setEventImage(eventInfoParams.event.iconId.let { it!! }.toInt())
    getEventOrganizer(organizerId)
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

  override fun clickedFinishVotingButton() {
    val disposable = getFilledQuestionnairesUseCase.get(eventInfoParams.event.id.let { it!! })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ questionnaire ->
          if (questionnaire != -1) {
            val sortedDateByVotes = (questionnaire as Questionnaire)
                .dateQuestionnaire?.values
                ?.groupBy { it.timestamp }
                ?.map { it.key to it.value.size }
                ?.sortedByDescending { it.second }
            val sortedVenuesByVotes = questionnaire.venueQuestionnaire?.values
                ?.groupBy { it.venueId }
                ?.map { it.key to it.value.size }
                ?.sortedByDescending { it.second }
            if (sortedDateByVotes != null && sortedVenuesByVotes != null) {
              setEventDate(sortedDateByVotes.first().first)
              setEventVenue(sortedVenuesByVotes.first().first)
              setFinishedPlanningFlag()
              view.startConfirmedEventActivity(EventIdParams(eventId = eventId))
            } else {
              view.showToast("Firstly somebody have to vote on date and venue of this event!")
            }
          } else {
            view.showToast("Firstly somebody have to vote on date and venue of this event!")
          }
        })
    disposables?.add(disposable)
  }

  private fun setFinishedPlanningFlag() {
    val disposable = RxFirebaseDatabase
        .setValue(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_FINISHED_VOTING), true)
        .subscribe()
    disposables?.add(disposable)
  }

  private fun setEventVenue(eventVenue: String?) {
    val disposable = RxFirebaseDatabase
        .setValue(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_EVENT_VENUE), eventVenue)
        .subscribe()
    disposables?.add(disposable)
  }

  private fun setEventDate(eventDate: String?) {
    val disposable = RxFirebaseDatabase
        .setValue(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_EVENT_DATE), eventDate)
        .subscribe()
    disposables?.add(disposable)
  }


  override fun navigateToEventChat() {
    view.startEventChatActivity(EventBasicInfoParams(eventInfoParams.event))
  }

  override fun navigateToEventDescription() {
    view.switchToEventDescriptionFragment(eventInfoParams)
  }

  override fun navigateToEventQuestionnaire() {
    view.switchToEventQuestionnaireFragment(EventBasicInfoParams(eventInfoParams.event))
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