package com.example.marcin.meetfriends.ui.place_details;

import com.example.marcin.meetfriends.ui.common.PlaceIdParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-12-29.
 */
@Module
public abstract class PlaceDetailsModule {

  @Binds
  abstract PlaceDetailsContract.View bindView(PlaceDetailsActivity view);

  @Binds
  abstract PlaceDetailsContract.Presenter bindPresenter(PlaceDetailsPresenter presenter);

  @Provides
  static PlaceIdParams providePlaceIdParams(PlaceDetailsActivity activity) {
    return new PlaceIdParams(activity.getIntent().getExtras());
  }
}
