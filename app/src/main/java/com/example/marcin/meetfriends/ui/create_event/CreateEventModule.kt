package com.example.marcin.meetfriends.ui.create_event

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-27.
 */
@Module
abstract class CreateEventModule {

  @Binds
  abstract fun bindView(view: CreateEventDialogFragment): CreateEventContract.View

  @Binds
  abstract fun bindPresenter(presenter: CreateEventPresenter): CreateEventContract.Presenter
}