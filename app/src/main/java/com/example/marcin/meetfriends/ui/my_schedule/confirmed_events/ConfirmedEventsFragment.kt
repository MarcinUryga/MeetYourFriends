package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.confirmed_event_detail.ConfirmedEventActivity
import com.example.marcin.meetfriends.ui.my_schedule.BaseMyScheduleFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_available_events.*

/**
 * Created by MARCIN on 2017-11-13.
 */

class ConfirmedEventsFragment : BaseMyScheduleFragment<ConfirmedEventsContract.Presenter>(), ConfirmedEventsContract.View {

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun showNoEventsView() {
    noEventsLayout.visibility = View.VISIBLE
    progressBar.visibility = View.INVISIBLE
    noEventsTextView.text = getString(R.string.no_confirmed_events_yet)
  }

  override fun startConfirmedEventActivity(eventIdParams: EventIdParams) {
    startActivity(ConfirmedEventActivity.newIntent(context, eventIdParams))
  }
}