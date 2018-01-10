package com.example.marcin.meetfriends.ui.charts;

import com.example.marcin.meetfriends.ui.common.params.EventIdParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2018-01-02.
 */

@Module
public abstract class ChartsModule {

  @Binds
  abstract public ChartsContract.View bindsView(ChartsDialogFragment view);

  @Binds
  abstract public ChartsContract.Presenter bindsPresenter(ChartsPresenter presenter);

  @Provides
  static EventIdParams provideBasicInfoParams(ChartsDialogFragment view) {
    return new EventIdParams(view.getArguments());
  }
}
