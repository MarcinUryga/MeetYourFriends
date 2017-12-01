package com.example.marcin.meetfriends.ui.event_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.event_detail.adapter.ParticipantsAdapter
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.ui.main.viewmodel.BottomBarEnum
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_details.*
import kotlinx.android.synthetic.main.item_event_description.*

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
  }

  override fun showParticipantsProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideParticipantsProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun showEventDescriptionProgressBar() {
    eventDescriptionTextView.visibility = View.INVISIBLE
    descriptionProgressBar.visibility = View.VISIBLE
  }

  override fun hideEventDescriptionProgressBar() {
    descriptionProgressBar.visibility = View.INVISIBLE
  }

  override fun showEventDetails(event: Event) {
    prepareToolbar(event.name)
    showEventDescription(event)
  }

  private fun showEventDescription(event: Event) {
    eventDescriptionTextView.visibility = View.VISIBLE
    eventDescriptionTextView.text = event.description
  }

  override fun showParticipants(participants: List<User>) {
    noParticipantsLayout.visibility = View.INVISIBLE
    participantsAdapter = ParticipantsAdapter(participants)
    participantsRecyclerView.adapter = participantsAdapter
  }

  override fun showNoParticipantsLayout() {
    noParticipantsLayout.visibility = View.VISIBLE
  }

  override fun startFriendsFragment(eventId: String) {
    startActivity(MainActivity.newIntent(baseContext, BottomBarEnum.FRIENDS.itemId))
  }

  private fun prepareToolbar(name: String?) {
    toolbar.title = name
//    setSupportActionBar(toolbar)
  }

  companion object {
    fun newIntent(context: Context, params: EventDetailsParams): Intent {
      val intent = Intent(context, EventDetailActivity::class.java)
      intent.putExtras(params.data)
      return intent
    }
  }
}