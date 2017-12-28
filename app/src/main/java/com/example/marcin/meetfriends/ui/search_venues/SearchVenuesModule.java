package com.example.marcin.meetfriends.ui.search_venues;

import android.content.Context;
import android.location.LocationManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-12-24.
 */
@Module
public abstract class SearchVenuesModule {

  @Binds
  abstract SearchVenuesContract.View bindView(SearchVenuesActivity view);

  @Binds
  abstract SearchVenuesContract.Presenter bindPresenter(SearchVenuesPresenter presenter);

/*  @Provides
  LocationManager provideLocationManager(SearchVenuesActivity view) {
    return (LocationManager) view.getSystemService(Context.LOCATION_SERVICE);
  }*/
}
