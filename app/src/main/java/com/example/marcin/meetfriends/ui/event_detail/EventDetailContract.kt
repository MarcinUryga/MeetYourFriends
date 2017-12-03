package com.example.marcin.meetfriends.ui.event_detail

import android.content.res.Resources
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.friends.ParticipantsListParams

/**
 * Created by marci on 2017-11-28.
 */
interface EventDetailContract {

  interface View : MvpView {

    fun showInviteFriendsButton()

    fun setUpOrganizerData(organizer: User)

    fun showEventDescriptionProgressBar()

    fun hideEventDescriptionProgressBar()

    fun showParticipantsProgressBar()

    fun hideParticipantsProgressBar()

    fun showEventDetails(event: Event)

    fun showParticipants(participants: List<User>)

    fun showNoParticipantsLayout()

    fun startFriendsActivity(eventIdParams: EventIdParams, participantsListParams: ParticipantsListParams)

    fun startEventChatActivity(params: EventIdParams)

    fun startEventVoteActivity(eventIdParams: EventIdParams)

    fun openDeleteButtonDialog(message: String)

    fun startMainActivity()
  }

  interface Presenter : MvpPresenter {

    fun navigateToFriendsFragment()

    fun navigateToEventChat()

    fun navigateToEventQuestionnaire()

    fun onDeleteClicked(resources: Resources)

    fun deleteEvent()
  }
}