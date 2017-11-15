package com.example.marcin.meetfriends.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.friends.FriendsFragment
import com.example.marcin.meetfriends.ui.menu_navigation.MainNavigationActivity
import com.example.marcin.meetfriends.ui.my_schedule.MyScheduleFragment
import com.example.marcin.meetfriends.ui.venues.VenuesFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("CheckResult")
class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    bottomNavigationView.selectedItemId = R.id.schedule
    switchFragment(MyScheduleFragment())
    navigateWithBottomView()
    drawrMenuButton.setOnClickListener {
      startActivity(MainNavigationActivity.newIntent(this))
    }
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

  /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }*/

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }
}
