package com.example.marcin.meetfriends.ui.my_schedule.planned_events

import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsContract

/**
 * Created by marci on 2018-01-04.
 */

interface PlannedEventsContract {

  interface View : BaseLoadEventsContract.View {

    fun startEventDetailActivity(eventBasicInfoParams: EventBasicInfoParams)
  }

  interface Presenter : BaseLoadEventsContract.Presenter
}