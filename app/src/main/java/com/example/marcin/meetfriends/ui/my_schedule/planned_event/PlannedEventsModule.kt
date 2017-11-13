package com.example.marcin.meetfriends.ui.my_schedule.planned_event

import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.PlannedEventsContract
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.PlannedEventsPresenter
import dagger.Binds
import dagger.Module

/**
 * Created by MARCIN on 2017-11-13.
 */
@Module
abstract class PlannedEventsModule {

  @Binds
  abstract fun bindView(view: PlannedEventsFragment): PlannedEventsContract.View

  @Binds
  abstract fun bindPresenter(presenter: PlannedEventsPresenter): PlannedEventsContract.Presenter
}
