package com.example.marcin.meetfriends.ui.chat_rooms

import dagger.Binds
import dagger.Module

/**
 * Created by marci on 2017-11-20.
 */
@Module
abstract class ChatRoomsModule {

  @Binds
  abstract fun bindView(view: ChatRoomsFragment): ChatRoomsContract.View

  @Binds
  abstract fun bindPresenter(presenter: ChatRoomsPresenter): ChatRoomsContract.Presenter
}