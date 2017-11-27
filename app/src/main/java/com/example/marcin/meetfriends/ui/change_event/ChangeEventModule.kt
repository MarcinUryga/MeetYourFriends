package com.example.marcin.meetfriends.ui.change_event

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-27.
 */
@Module
abstract class ChangeEventModule {

  @Binds
  abstract fun bindView(view: ChangeEventDialogFragment): ChangeEventContract.View

  @Binds
  abstract fun bindPresenter(presenter: ChangeEventPresenter): ChangeEventContract.Presenter
}