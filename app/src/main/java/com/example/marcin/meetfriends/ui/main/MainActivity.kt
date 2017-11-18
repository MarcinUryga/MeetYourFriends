package com.example.marcin.meetfriends.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.ActionBarExtensions
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.friends.FriendsFragment
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.my_schedule.MyScheduleFragment
import com.example.marcin.meetfriends.ui.venues.VenuesFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    bottomNavigationView.selectedItemId = R.id.schedule
    switchFragment(MyScheduleFragment())
    navigateWithBottomView()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.logout -> presenter.logout()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun setUpActionBar(uri: String) {
    ActionBarExtensions.loadUserIcon(this, supportActionBar, uri)
  }

  override fun startLoginActivity() {
    finish()
    startActivity(LoginActivity.newIntent(this))
  }

  private fun navigateWithBottomView() {
    bottomNavigationView
        .setOnNavigationItemSelectedListener { item ->
          when (item.itemId) {
            R.id.schedule -> switchFragment(MyScheduleFragment())
            R.id.friends -> switchFragment(FriendsFragment())
            R.id.venues -> switchFragment(VenuesFragment())
            else -> throw Exception("Illegal fragment")
          }
          true
        }
  }

  private fun switchFragment(currentFragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, currentFragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }
}
