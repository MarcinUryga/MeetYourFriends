package com.example.marcin.meetfriends.ui.launch

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-11.
 */
@Module
abstract class LaunchModule {

  @Binds
  abstract fun bindView(view: LaunchActivity): LaunchContract.View

  @Binds
  abstract fun bindPresenter(presenter: LaunchPresenter): LaunchContract.Presenter
}