package com.example.marcin.meetfriends.ui.event_detail

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
    sharedPreferences: SharedPreferences
) : BasePresenter<EventDetailContract.View>(), EventDetailContract.Presenter {

  private val sharedPref = SharedPref(sharedPreferences)
  private lateinit var event: Event

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
          this.event = event
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
          if (participants.isEmpty()) {
            view.showNoParticipantsLayout()
          } else {
            view.showParticipants(participants)
          }
        })
    disposables?.add(disposable)
  }

  override fun navigateToFriendsFragment() {
//    TODO("handle event it in FriendsActivity")
    sharedPref.saveChosenEvent(event.id.let { it!! })
    view.startFriendsActivity(EventIdParams(event.id.let { it!! }))
  }

  override fun navigateToEventChat() {
    view.startEventChatActivity(EventIdParams(event.id.let { it!! }))
  }

  override fun navigateToEventQuestionnaire() {
    view.startEventVoteActivity(EventIdParams(event.id.let { it!! }))
  }

  override fun onDeleteClicked() {
    view.showDeleteEventDialog()
  }

  override fun deleteEvent() {
    view.startMainActivity()
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(event.id).removeValue()
  }
}