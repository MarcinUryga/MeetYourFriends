package com.example.marcin.meetfriends.ui.event_detail.event_description

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.friends.ParticipantsListParams
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent

/**
 * Created by marci on 2017-12-03.
 */
interface EventDescriptionContract {

  interface View : MvpView {

    fun showEventDescription(eventDescription: String)

    fun showInviteFriendsButton()

    fun startFriendsActivity(eventIdParams: EventIdParams, participantsListParams: ParticipantsListParams)

    fun showParticipantsProgressBar()

    fun hideParticipantsProgressBar()

//    fun showParticipants(participant: User)

    fun manageEvent(post: RxFirebaseChildEvent<DataSnapshot>)

    fun showNoParticipantsLayout()
  }

  interface Presenter : MvpPresenter {

    fun navigateToFriendsFragment(participants: MutableList<User>)
  }
}