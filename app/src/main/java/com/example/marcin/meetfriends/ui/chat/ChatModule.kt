package com.example.marcin.meetfriends.ui.chat

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-21.
 */
@Module
abstract class ChatModule {

  @Binds
  abstract fun bindView(view: ChatActivity): ChatContract.View

  @Binds
  abstract fun bindPresenter(presenter: ChatPresenter): ChatContract.Presenter
}