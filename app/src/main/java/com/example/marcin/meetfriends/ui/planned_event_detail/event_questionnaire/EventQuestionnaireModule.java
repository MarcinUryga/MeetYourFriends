package com.example.marcin.meetfriends.ui.planned_event_detail.event_questionnaire;

import android.content.Context;
import android.location.LocationManager;

import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-12-05.
 */
@Module
public abstract class EventQuestionnaireModule {

  @Binds
  abstract EventQuestionnaireContract.View bindView(EventQuestionnaireFragment view);

  @Binds
  abstract EventQuestionnaireContract.Presenter bindPresenter(EventQuestionnairePresenter presenter);

  @Provides
  static EventBasicInfoParams provideBasicInfoParams(EventQuestionnaireFragment view) {
    return new EventBasicInfoParams(view.getArguments());
  }

  @Provides
  public static LocationManager provideLocationManager(EventQuestionnaireFragment view) {
    return (LocationManager) view.getActivity().getSystemService(Context.LOCATION_SERVICE);
  }
}
