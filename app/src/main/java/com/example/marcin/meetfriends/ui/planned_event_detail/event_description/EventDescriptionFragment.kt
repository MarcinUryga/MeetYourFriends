package com.example.marcin.meetfriends.ui.planned_event_detail.event_description

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.planned_event_detail.adapter.ParticipantsAdapter
import com.example.marcin.meetfriends.ui.friends.FriendsActivity
import com.example.marcin.meetfriends.ui.friends.ParticipantsListParams
import com.google.firebase.database.DataSnapshot
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.content_event_description.*
import kotlinx.android.synthetic.main.item_event_description.*
import kotlinx.android.synthetic.main.item_event_participants.*

/**
 * Created by marci on 2017-12-03.
 */
class EventDescriptionFragment : BaseFragment<EventDescriptionContract.Presenter>(), EventDescriptionContract.View {

  private var participantsAdapter = ParticipantsAdapter()
//  private lateinit var participantsAdapter: ParticipantsAdapter

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.content_event_description, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    participantsRecyclerView.layoutManager = GridLayoutManager(context, 3)
    inviteFriendsButton.setOnClickListener {
      presenter.navigateToFriendsFragment(participantsAdapter.getParticipantsList())
    }
  }

  override fun manageEvent(post: RxFirebaseChildEvent<DataSnapshot>) {
    noParticipantsLayout.visibility = View.INVISIBLE
    eventParticipantsLayout.visibility = View.VISIBLE
    participantsAdapter.manageChildItem(post)
    participantsRecyclerView.adapter = participantsAdapter
  }

  override fun showEventDescription(eventDescription: String) {
    eventDescriptionLayout.visibility = View.VISIBLE
    eventDescriptionTextView.text = eventDescription
  }

  override fun showInviteFriendsButton() {
    inviteFriendsButton.visibility = View.VISIBLE
  }

  override fun hideInviteFriendsButton(){
    inviteFriendsButton.visibility = View.GONE
  }

  override fun showParticipantsProgressBar() {
    eventParticipantsLayout.visibility = View.INVISIBLE
    eventParticipantsProgressBar.visibility = View.VISIBLE
  }

  override fun hideParticipantsProgressBar() {
    eventParticipantsProgressBar.visibility = View.INVISIBLE
  }

  /*  override fun showParticipants(participant: User) {
      noParticipantsLayout.visibility = View.INVISIBLE
      eventParticipantsLayout.visibility = View.VISIBLE
  //    participantsAdapter.addParticipant(participant)
      participantsRecyclerView.adapter = participantsAdapter
    }*/
/*  override fun showParticipants(participant: User) {
    noParticipantsLayout.visibility = View.INVISIBLE
    eventParticipantsLayout.visibility = View.VISIBLE
    participantsAdapter.addParticipant(participant)
    participantsRecyclerView.adapter = participantsAdapter
  }*/

  override fun showNoParticipantsLayout() {
    noParticipantsLayout.visibility = View.VISIBLE
    inviteFriendsButton.visibility = View.VISIBLE
    inviteFriendsButton.text = getString(R.string.invite_friends)
  }

  override fun startFriendsActivity(eventIdParams: EventIdParams, participantsListParams: ParticipantsListParams) {
    startActivity(FriendsActivity.newIntent(context, eventIdParams, participantsListParams))
  }
}