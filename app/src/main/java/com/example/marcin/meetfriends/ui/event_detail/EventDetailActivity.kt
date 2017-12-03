package com.example.marcin.meetfriends.ui.event_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.event_detail.adapter.ParticipantsAdapter
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.ui.main.viewmodel.BottomBarEnum
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_details.*
import kotlinx.android.synthetic.main.item_event_description.*
import kotlinx.android.synthetic.main.item_event_participants.*

/**
 * Created by marci on 2017-11-28.
 */
class EventDetailActivity : BaseActivity<EventDetailContract.Presenter>(), EventDetailContract.View {

  private lateinit var participantsAdapter: ParticipantsAdapter

  @SuppressLint("CheckResult")
  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_event_detail)
    participantsRecyclerView.layoutManager = GridLayoutManager(baseContext, 3)
    inviteFriendsButton.setOnClickListener {
      presenter.navigateToFriendsFragment()
    }
    openChatButton.setOnClickListener {
      presenter.navigateToEventChat()
    }
    openVoteButton.setOnClickListener {
      presenter.navigateToEventQuestionnaire()
    }
    deleteEventButton.setOnClickListener {
      presenter.onDeleteClicked()
    }
  }

  override fun showEventDescriptionProgressBar() {
    eventDescriptionLayout.visibility = View.INVISIBLE
    descriptionProgressBar.visibility = View.VISIBLE
  }

  override fun hideEventDescriptionProgressBar() {
    descriptionProgressBar.visibility = View.INVISIBLE
  }

  override fun showParticipantsProgressBar() {
    eventParticipantsLayout.visibility = View.INVISIBLE
    eventParticipantsProgressBar.visibility = View.VISIBLE
  }

  override fun hideParticipantsProgressBar() {
    eventParticipantsProgressBar.visibility = View.INVISIBLE
  }

  override fun showEventDetails(event: Event) {
    prepareToolbar(event.name)
    showEventDescription(event)
  }

  override fun showParticipants(participants: List<User>) {
    noParticipantsLayout.visibility = View.INVISIBLE
    eventParticipantsLayout.visibility = View.VISIBLE
    participantsAdapter = ParticipantsAdapter(participants)
    participantsRecyclerView.adapter = participantsAdapter
  }

  override fun showNoParticipantsLayout() {
    noParticipantsLayout.visibility = View.VISIBLE
  }

  override fun startFriendsFragment(eventId: String) {
    startActivity(MainActivity.newIntent(baseContext, BottomBarEnum.QUESTIONNAIRES.itemId))
  }

  override fun startEventChatActivity(params: EventIdParams) {
    startActivity(ChatActivity.newIntent(baseContext, params))
  }

  override fun startEventVoteActivity(eventIdParams: EventIdParams) {
    Toast.makeText(baseContext, "Questonnarie to: ${eventIdParams.data}", Toast.LENGTH_SHORT).show()
  }

  private fun prepareToolbar(name: String?) {
    toolbar.title = name
    setSupportActionBar(toolbar)
  }

  override fun setUpOrganizerData(user: User) {
    organizerDisplayNameTextView.text = user.displayName
    Picasso.with(baseContext).load(user.photoUrl).transform(CircleTransform()).into(organizerPhoto)
  }

  override fun showDeleteEventDialog() {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.delete_event))
        .setMessage(getString(R.string.do_you_really_want_to_delete_this_event))
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton(android.R.string.yes) { _, _ ->
          presenter.deleteEvent()
        }
        .setNegativeButton(android.R.string.no, null)
        .show()
  }

  override fun startMainActivity() {
    startActivity(MainActivity.newIntent(baseContext))
  }

  private fun showEventDescription(event: Event) {
    eventDescriptionLayout.visibility = View.VISIBLE
    eventDescriptionTextView.text = event.description
  }

  companion object {
    fun newIntent(context: Context, params: EventIdParams): Intent {
      val intent = Intent(context, EventDetailActivity::class.java)
      intent.putExtras(params.data)
      return intent
    }
  }
}