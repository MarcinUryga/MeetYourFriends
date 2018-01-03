package com.example.marcin.meetfriends.ui.confirmed_event_detail;

import android.content.Context;
import android.location.LocationManager;

import com.example.marcin.meetfriends.ui.common.EventIdParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2018-01-03.
 */
@Module
public abstract class ConfirmedEventModule {

  @Binds
  abstract ConfirmedEventContract.View bindsView(ConfirmedEventActivity view);

  @Binds
  abstract ConfirmedEventContract.Presenter bindsPresenter(ConfirmedEventPresenter presenter);

  @Provides
  static EventIdParams provideBasicInfoParams(ConfirmedEventActivity activity) {
    return new EventIdParams(activity.getIntent().getExtras());
  }

  @Provides
  public static LocationManager provideLocationManager(ConfirmedEventActivity view) {
    return (LocationManager) view.getActivity().getSystemService(Context.LOCATION_SERVICE);
  }
}
