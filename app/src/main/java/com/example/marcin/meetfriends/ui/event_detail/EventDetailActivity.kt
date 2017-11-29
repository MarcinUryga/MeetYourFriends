package com.example.marcin.meetfriends.ui.event_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import dagger.android.AndroidInjection

/**
 * Created by marci on 2017-11-28.
 */
class EventDetailActivity : BaseActivity<EventDetailContract.Presenter>(), EventDetailContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_event_detail)
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, EventDetailActivity::class.java)
    }
  }
}