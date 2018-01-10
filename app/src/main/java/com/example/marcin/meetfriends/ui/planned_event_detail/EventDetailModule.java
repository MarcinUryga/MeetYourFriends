package com.example.marcin.meetfriends.ui.planned_event_detail;

import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-11-28.
 */
@Module
public abstract class EventDetailModule {

  @Binds
  public abstract EventDetailContract.View bindView(EventDetailActivity view);

  @Binds
  public abstract EventDetailContract.Presenter bindPresenter(EventDetailPresenter presenter);

  @Provides
  static EventBasicInfoParams provideBasicInfoParams(EventDetailActivity activity) {
    return new EventBasicInfoParams(activity.getIntent().getExtras());
  }
}