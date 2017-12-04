package com.example.marcin.meetfriends.ui.event_detail.event_description;

import com.example.marcin.meetfriends.ui.event_detail.EventBasicInfoParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-12-03.
 */
@Module
public abstract class EventDescriptionModule {

  @Binds
  abstract EventDescriptionContract.View bindView(EventDescriptionFragment view);

  @Binds
  abstract EventDescriptionContract.Presenter bindPresenter(EventDescriptionPresenter presenter);

  @Provides
  static EventBasicInfoParams provideBasicInfoParams(EventDescriptionFragment view) {
    return new EventBasicInfoParams(view.getArguments());
  }
}
