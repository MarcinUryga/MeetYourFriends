package com.example.marcin.meetfriends.ui.event_detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.event_description.EventDescriptionFragment
import com.example.marcin.meetfriends.ui.event_detail.event_questionnaire.EventQuestionnaireFragment
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.FragmentsItems
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_details.*

/**
 * Created by marci on 2017-11-28.
 */
class EventDetailActivity : BaseActivity<EventDetailContract.Presenter>(), EventDetailContract.View {

  @SuppressLint("CheckResult")
  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_event_detail)
    setUpNavigateButtons()
    navigateToCurrentFragment()
  }

  private fun navigateToCurrentFragment() {
    when {
      intent.extras.getInt(FRAGMENT_TO_OPEN, 1) == FragmentsItems.DESCRIPTION.fragmentId -> presenter.navigateToEventDescription()
      intent.extras.getInt(FRAGMENT_TO_OPEN, 1) == FragmentsItems.QUESTIONNAIRES.fragmentId -> presenter.navigateToEventQuestionnaire()
    }
  }

  override fun setUpToolbarEventName(eventName: String) {
    prepareToolbar(eventName)
  }

  override fun startEventChatActivity(params: EventBasicInfoParams) {
    startActivity(ChatActivity.newIntent(baseContext, params))
  }

  override fun switchToEventDescriptionFragment(arguments: EventBasicInfoParams) {
    openDescriptionButton.visibility = View.GONE
    openQuestionnaireButton.visibility = View.VISIBLE
    switchCurrentFragmente(EventDescriptionFragment(), arguments)
  }

  override fun switchToEventQuestionnaireFragment(arguments: EventBasicInfoParams) {
    openDescriptionButton.visibility = View.VISIBLE
    openQuestionnaireButton.visibility = View.GONE
    switchCurrentFragmente(EventQuestionnaireFragment(), arguments)
  }

  private fun setUpNavigateButtons() {
    openDescriptionButton.setOnClickListener {
      presenter.navigateToEventDescription()
    }
    openChatButton.setOnClickListener {
      presenter.navigateToEventChat()
    }
    openQuestionnaireButton.setOnClickListener {
      presenter.navigateToEventQuestionnaire()
    }
    deleteEventButton.setOnClickListener {
      presenter.onDeleteClicked(resources)
    }
  }

  private fun prepareToolbar(name: String?) {
    toolbar.title = name
    setSupportActionBar(toolbar)
  }

  override fun setUpOrganizerData(user: User) {
    organizerDisplayNameTextView.text = user.displayName
    Picasso.with(baseContext).load(user.photoUrl).transform(CircleTransform()).into(organizerPhoto)
  }

  override fun openDeleteButtonDialog(message: String) {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.delete_event))
        .setMessage(message)
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

  private fun switchCurrentFragmente(currentFragment: Fragment, arguments: EventBasicInfoParams) {
    currentFragment.arguments = arguments.data
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, currentFragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()
  }

  companion object {
    private const val FRAGMENT_TO_OPEN = "openFragment"

    fun newIntent(context: Context, eventBasicInfoParams: EventBasicInfoParams, fragmentItem: FragmentsItems = FragmentsItems.DESCRIPTION): Intent {
      val intent = Intent(context, EventDetailActivity::class.java)
      intent.putExtras(eventBasicInfoParams.data)
      intent.putExtra(FRAGMENT_TO_OPEN, fragmentItem.fragmentId)
      return intent
    }
  }
}