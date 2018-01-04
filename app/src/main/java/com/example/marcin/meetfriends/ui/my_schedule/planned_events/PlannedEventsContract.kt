package com.example.marcin.meetfriends.ui.my_schedule.planned_events

import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.my_schedule.BaseMyScheduleContract

/**
 * Created by marci on 2018-01-04.
 */

interface PlannedEventsContract {

  interface View : BaseMyScheduleContract.View {

    fun startEventDetailActivity(eventBasicInfoParams: EventBasicInfoParams)
  }

  interface Presenter : BaseMyScheduleContract.Presenter
}