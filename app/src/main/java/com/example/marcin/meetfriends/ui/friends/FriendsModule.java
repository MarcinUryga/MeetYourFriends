package com.example.marcin.meetfriends.ui.friends;

import com.example.marcin.meetfriends.ui.common.EventIdParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-12-03.
 */

@Module
public abstract class FriendsModule {

  @Binds
  abstract FriendsContract.View bindView(FriendsActivity view);

  @Binds
  abstract FriendsContract.Presenter bindPresenter(FriendsPresenter presenter);

  @Provides
  public static EventIdParams providesEventIdParams(FriendsActivity activity) {
    return new EventIdParams(activity.getIntent().getExtras());
  }

  @Provides
  public static ParticipantsListParams providesParticipantsListParams(FriendsActivity activity) {
    return new ParticipantsListParams(activity.getIntent().getExtras());
  }
}
