package com.example.marcin.meetfriends.ui.confirmed_event_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import dagger.android.AndroidInjection

/**
 * Created by marci on 2018-01-03.
 */
class ConfirmedEventActivity : BaseActivity<ConfirmedEventContract.Presenter>(), ConfirmedEventContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_confirmed_event)
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, ConfirmedEventActivity::class.java)
    }
  }
}