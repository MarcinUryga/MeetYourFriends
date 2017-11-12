package com.example.marcin.meetfriends.ui.main

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-09.
 */

@Module
abstract class MainModule {

  @Binds
  abstract fun bindView(view: MainActivity): MainContract.View

  @Binds
  abstract fun bindPresenter(presenter: MainPresenter): MainContract.Presenter
}