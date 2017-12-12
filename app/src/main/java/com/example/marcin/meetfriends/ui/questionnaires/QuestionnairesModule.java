package com.example.marcin.meetfriends.ui.questionnaires;

import dagger.Binds;
import dagger.Module;

/**
 * Created by marci on 2017-12-12.
 */
@Module
public abstract class QuestionnairesModule {

    @Binds
    abstract QuestionairesContract.View bindView(QuestionnairesFragment view);

    @Binds
    abstract QuestionairesContract.Presenter bindPresenter(QuestionnairesPresenter presenter);
}
