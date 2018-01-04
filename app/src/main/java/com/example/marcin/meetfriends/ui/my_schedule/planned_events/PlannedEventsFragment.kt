package com.example.marcin.meetfriends.ui.my_schedule.planned_events

import android.annotation.SuppressLint
import android.content.Context
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.my_schedule.BaseMyScheduleFragment
import com.example.marcin.meetfriends.ui.planned_event_detail.EventDetailActivity
import dagger.android.support.AndroidSupportInjection

/**
 * Created by marci on 2018-01-04.
 */

class PlannedEventsFragment : BaseMyScheduleFragment<PlannedEventsContract.Presenter>(), PlannedEventsContract.View {

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun startEventDetailActivity(eventBasicInfoParams: EventBasicInfoParams) {
    startActivity(EventDetailActivity.newIntent(context, eventBasicInfoParams))
  }
}