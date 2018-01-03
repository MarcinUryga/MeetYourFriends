package com.example.marcin.meetfriends.ui.confirmed_event_detail;

import dagger.Binds;
import dagger.Module;

/**
 * Created by marci on 2018-01-03.
 */
@Module
public abstract class ConfirmedEventModule {

  @Binds
  abstract ConfirmedEventContract.View bindsView(ConfirmedEventActivity view);

  @Binds
  abstract ConfirmedEventContract.Presenter bindsPresenter(ConfirmedEventPresenter presenter);
}
