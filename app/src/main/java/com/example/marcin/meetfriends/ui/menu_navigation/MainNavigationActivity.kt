package com.example.marcin.meetfriends.ui.menu_navigation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.friends.FriendsFragment
import com.example.marcin.meetfriends.ui.my_schedule.MyScheduleFragment
import com.example.marcin.meetfriends.ui.venues.VenuesFragment
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main_navigation.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainNavigationActivity : BaseActivity<MainNavigationContract.Presenter>(), MainNavigationContract.View {

  lateinit var drawerToggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_navigation)
    setSupportActionBar(toolbar)
    drawerToggle = setupDrawerToggle()
    drawerLayout.addDrawerListener(drawerToggle)
    setupNavigationDrawer()
    switchFragment(MyScheduleFragment())
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    drawerToggle.syncState()
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    drawerToggle.onConfigurationChanged(newConfig)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return if (drawerToggle.onOptionsItemSelected(item)) {
      true
    } else super.onOptionsItemSelected(item)
  }

  override fun setNavigationHeader(user: FirebaseUser?) {

  }

  private fun setupDrawerToggle(): ActionBarDrawerToggle {
    return ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
  }

  private fun setupNavigationDrawer() {
    navigationView.setNavigationItemSelectedListener { menuItem ->
      when (menuItem.itemId) {
        R.id.nav_schedule -> switchFragment(MyScheduleFragment())
        R.id.nav_friends -> switchFragment(FriendsFragment())
        R.id.nav_venues -> switchFragment(VenuesFragment())
        R.id.logout -> switchFragment(VenuesFragment())
        else -> throw Exception("Illegal fragment")
      }
      menuItem.isChecked = true
      title = menuItem.title
      drawerLayout.closeDrawers()
      true
    }
  }

  private fun switchFragment(currentFragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.navContainer, currentFragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit()
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, MainNavigationActivity::class.java)
    }
  }
}
