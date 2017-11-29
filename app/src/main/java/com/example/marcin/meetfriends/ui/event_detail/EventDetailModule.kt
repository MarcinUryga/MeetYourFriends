package com.example.marcin.meetfriends.ui.event_detail

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-28.
 */
@Module
abstract class EventDetailModule {

  @Binds
  abstract fun bindView(view: EventDetailActivity): EventDetailContract.View

  @Binds
  abstract fun bindPresenter(presenter: EventDetailPresenter): EventDetailContract.Presenter
}