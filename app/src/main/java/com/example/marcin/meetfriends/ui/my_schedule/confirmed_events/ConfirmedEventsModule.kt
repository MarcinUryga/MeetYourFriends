package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import dagger.Binds
import dagger.Module

/**
 * Created by MARCIN on 2017-11-13.
 **/
@Module
abstract class ConfirmedEventsModule {

  @Binds
  abstract fun bindView(view: ConfirmedEventsFragment): ConfirmedEventsContract.View

  @Binds
  abstract fun bindPresenter(presenter: ConfirmedEventsPresenter): ConfirmedEventsContract.Presenter
}
