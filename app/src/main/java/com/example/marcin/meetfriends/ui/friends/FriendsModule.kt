package com.example.marcin.meetfriends.ui.friends

import dagger.Binds
import dagger.Module

/**
 * Created by MARCIN on 2017-11-13.
 */
@Module
abstract class FriendsModule {

  @Binds
  abstract fun bindView(view: FriendsFragment): FriendsContract.View

  @Binds
  abstract fun bindPresenter(presenter: FriendsPresenter): FriendsContract.Presenter
}