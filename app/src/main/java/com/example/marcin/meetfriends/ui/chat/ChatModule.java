package com.example.marcin.meetfriends.ui.chat;

import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams;

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
  static EventBasicInfoParams provideBasicInfoParams(ChatActivity view) {
    return new EventBasicInfoParams(view.getIntent().getExtras());
  }
}
