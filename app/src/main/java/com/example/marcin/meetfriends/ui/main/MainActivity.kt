package com.example.marcin.meetfriends.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.ActionBarExtensions
import com.example.marcin.meetfriends.extensions.setEditTextHint
import com.example.marcin.meetfriends.extensions.setMargins
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.create_event.CreateEventDialogFragment
import com.example.marcin.meetfriends.ui.chat_rooms.ChatRoomsFragment
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.main.viewmodel.BottomBarEnum
import com.example.marcin.meetfriends.ui.my_schedule.MyScheduleFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by marci on 2017-11-09.
 */
class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    switchFragment(MyScheduleFragment(), BottomBarEnum.SCHEDULE.itemId)
    navigateWithBottomView()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.logout -> presenter.tryLogout()
      R.id.addEvent -> presenter.addNewEvent()
//      R.id.changeEvent -> presenter.changeEvent()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun setUpActionBar(uri: String) {
    ActionBarExtensions.loadUserIcon(this, supportActionBar, uri)
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    getCurrentFragment(intent)
  }

  override fun startLoginActivity() {
    finish()
    startActivity(LoginActivity.newIntent(this))
  }

  override fun showCreateEventDialog() {
    val eventNameEditText = EditText(this)
    val eventDescriptionEditText = EditText(this)
    val parentLayout = LinearLayout(this)
    parentLayout.addView(eventNameEditText.setMargins(45, 45, 10, 10).setEditTextHint(getString(R.string.event_name)))
//    parentLayout.addView(eventDescriptionEditText.setMargins(45, 45, 10, 10).setEditTextHint(getString(R.string.event_description)))
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.create_new_event))
        .setMessage(getString(R.string.name_your_event))
        .setView(parentLayout)
        .setPositiveButton(android.R.string.yes, { _, _ ->
          val eventName = eventNameEditText.text.toString()
          if (eventName.isNotEmpty()) {
            presenter.createEvent(eventNameEditText.text.toString(), eventDescriptionEditText.text.toString())
            Toast.makeText(this, eventNameEditText.text, Toast.LENGTH_SHORT).show()
          }
        })
        .setNegativeButton(android.R.string.no, null)
        .show()
  }

  override fun showChangeEventDialog() {
    val changeEventDialogFragment = CreateEventDialogFragment()
    changeEventDialogFragment.show(supportFragmentManager, "asdasd")
  }

  override fun showCreatedEventSnackBar(eventId: String) {
    Snackbar.make(snackBarContainer, getString(R.string.created_new_event), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeEvent(eventId)
        }).show()
  }

  override fun showConfirmLogoutDialog() {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.logout))
        .setMessage(getString(R.string.do_you_really_want_to_logout))
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton(android.R.string.yes) { _, _ ->
          presenter.logout()
        }
        .setNegativeButton(android.R.string.no, null)
        .show()
  }

  private fun getCurrentFragment(intent: Intent) {
    if (intent.hasExtra(FRAGMENT_TO_OPEN)) {
      val bottomItemId = intent.getIntExtra(FRAGMENT_TO_OPEN, -1)
      when (bottomItemId) {
        BottomBarEnum.SCHEDULE.itemId -> switchFragment(MyScheduleFragment(), bottomItemId)
        BottomBarEnum.QUESTIONNAIRES.itemId -> switchFragment(MyScheduleFragment(), bottomItemId)
//        BottomBarEnum.VENUES.itemId -> switchFragment(VenuesFragment(), bottomItemId)
        BottomBarEnum.CHAT.itemId -> switchFragment(ChatRoomsFragment(), bottomItemId)
        else -> switchFragment(MyScheduleFragment(), 1)
      }
    }
  }

  private fun navigateWithBottomView() {
    bottomNavigationView
        .setOnNavigationItemSelectedListener { item ->
          when (item.itemId) {
            R.id.schedule -> switchFragment(MyScheduleFragment())
            R.id.questionnaires -> switchFragment(MyScheduleFragment())
//            R.id.venues -> switchFragment(VenuesFragment())
            R.id.chatRooms -> switchFragment(ChatRoomsFragment())
            else -> throw Exception("Illegal fragment")
          }
          true
        }
  }

  private fun switchFragment(currentFragment: Fragment, bottomItem: Int = -1) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, currentFragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()
    if (bottomItem != -1) {
      bottomNavigationView.selectedItemId = bottomItem
    }
  }

  companion object {
    private const val FRAGMENT_TO_OPEN = "openFragment"

    fun newIntent(context: Context, bottomItem: Int = -1): Intent {
      val intent = Intent(context, MainActivity::class.java)
      intent.putExtra(FRAGMENT_TO_OPEN, bottomItem)
      return intent
    }
  }
}
