package com.example.marcin.meetfriends.ui.chat;

import com.example.marcin.meetfriends.ui.common.EventIdParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-12-03.
 */
@Module
abstract public class ChatModule {

  @Binds
  abstract ChatContract.View bindView(ChatActivity view);

  @Binds
  abstract ChatContract.Presenter bindPresenter(ChatPresenter presenter);

  @Provides
  static EventIdParams provideParams(ChatActivity activity) {
    return new EventIdParams(activity.getIntent().getExtras());
  }
}
