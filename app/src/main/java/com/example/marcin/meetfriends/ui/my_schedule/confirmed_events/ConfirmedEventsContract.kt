package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import com.example.marcin.meetfriends.ui.common.params.EventIdParams
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsContract

/**
 * Created by MARCIN on 2017-11-13.
 **/
interface ConfirmedEventsContract {

  interface View : BaseLoadEventsContract.View {

    fun startConfirmedEventActivity(eventIdParams: EventIdParams)
  }

  interface Presenter : BaseLoadEventsContract.Presenter
}
