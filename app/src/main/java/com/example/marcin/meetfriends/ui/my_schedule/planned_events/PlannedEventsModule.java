package com.example.marcin.meetfriends.ui.my_schedule.planned_events;

import dagger.Binds;
import dagger.Module;

/**
 * Created by marci on 2018-01-04.
 */

@Module
public abstract class PlannedEventsModule {

  @Binds
  abstract PlannedEventsContract.View bindsView(PlannedEventsFragment view);

  @Binds
  abstract PlannedEventsContract.Presenter bindPresenter(PlannedEventsPresenter presenter);
}
