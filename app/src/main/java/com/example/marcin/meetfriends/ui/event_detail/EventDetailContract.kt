package com.example.marcin.meetfriends.ui.event_detail

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventIdParams

/**
 * Created by marci on 2017-11-28.
 */
interface EventDetailContract {

  interface View : MvpView {

    fun setUpOrganizerData(organizer: User)

    fun showEventDescriptionProgressBar()

    fun hideEventDescriptionProgressBar()

    fun showParticipantsProgressBar()

    fun hideParticipantsProgressBar()

    fun showEventDetails(event: Event)

    fun showParticipants(participants: List<User>)

    fun showNoParticipantsLayout()

    fun startFriendsFragment(eventId: String)

    fun startEventChatActivity(params: EventIdParams)

    fun startEventVoteActivity(eventIdParams: EventIdParams)

    fun showDeleteEventDialog()

    fun startMainActivity()
  }

  interface Presenter : MvpPresenter {

    fun navigateToFriendsFragment()

    fun navigateToEventChat()

    fun navigateToEventQuestionnaire()

    fun onDeleteClicked()

    fun deleteEvent()
  }
}