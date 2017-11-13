package com.example.marcin.meetfriends.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.my_schedule.MyScheduleFragment
import dagger.android.AndroidInjection

@SuppressLint("CheckResult")
class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    switchFragment(MyScheduleFragment())
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
