package com.example.marcin.meetfriends.ui.place_details;

import dagger.Binds;
import dagger.Module;

/**
 * Created by marci on 2017-12-29.
 */
@Module
public abstract class PlaceDetailsModule {

  @Binds
  abstract PlaceDetailsContract.View bindView(PlaceDetailsActivity view);

  @Binds
  abstract PlaceDetailsContract.Presenter bindPresenter(PlaceDetailsPresenter presenter);
}
