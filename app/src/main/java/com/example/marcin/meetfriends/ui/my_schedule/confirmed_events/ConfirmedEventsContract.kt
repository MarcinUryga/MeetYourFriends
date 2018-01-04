package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.my_schedule.BaseMyScheduleContract

/**
 * Created by MARCIN on 2017-11-13.
 **/
interface ConfirmedEventsContract {

  interface View : BaseMyScheduleContract.View {

    fun startConfirmedEventActivity(eventIdParams: EventIdParams)
  }

  interface Presenter : BaseMyScheduleContract.Presenter
}
