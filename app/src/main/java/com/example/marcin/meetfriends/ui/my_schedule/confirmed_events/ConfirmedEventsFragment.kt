package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import android.annotation.SuppressLint
import android.content.Context
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.confirmed_event_detail.ConfirmedEventActivity
import com.example.marcin.meetfriends.ui.my_schedule.BaseMyScheduleFragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created by MARCIN on 2017-11-13.
 */

class ConfirmedEventsFragment : BaseMyScheduleFragment<ConfirmedEventsContract.Presenter>(), ConfirmedEventsContract.View {

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun startConfirmedEventActivity(eventIdParams: EventIdParams) {
    startActivity(ConfirmedEventActivity.newIntent(context, eventIdParams))
  }
}