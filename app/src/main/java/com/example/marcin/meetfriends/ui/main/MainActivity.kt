package com.example.marcin.meetfriends.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.ActionBarExtensions
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.chat_rooms.ChatRoomsFragment
import com.example.marcin.meetfriends.ui.create_event.CreateEventActivity
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.main.viewmodel.BottomBarEnum
import com.example.marcin.meetfriends.ui.my_schedule.MyScheduleFragment
import com.example.marcin.meetfriends.ui.questionnaires.QuestionnairesFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by marci on 2017-11-09.
 */
class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    supportActionBar?.title = " ${getString(R.string.manage_your_events)}"
    setContentView(R.layout.activity_main)
    switchFragment(MyScheduleFragment(), BottomBarEnum.SCHEDULE.itemId)
    navigateWithBottomView()
  }

  public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.logout -> presenter.tryLogout()
      R.id.addEvent -> presenter.addNewEvent()
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

  override fun startCreateEventActivity() {
    startActivity(CreateEventActivity.newIntent(baseContext))
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
        BottomBarEnum.QUESTIONNAIRES.itemId -> switchFragment(QuestionnairesFragment(), bottomItemId)
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
            R.id.questionnaires -> switchFragment(QuestionnairesFragment())
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
