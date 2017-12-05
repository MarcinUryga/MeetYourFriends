package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire;

import dagger.Binds;
import dagger.Module;

/**
 * Created by marci on 2017-12-05.
 */
@Module
public abstract class EventQuestionnaireModule {

    @Binds
    abstract EventQuestionnaireContract.View bindView(EventQuestionnaireFragment view);

    @Binds
    abstract EventQuestionnaireContract.Presenter bindPresenter(EventQuestionnairePresenter presenter);
}
