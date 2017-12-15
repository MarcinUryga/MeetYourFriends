package com.example.marcin.meetfriends.ui.choose_event_icon;

import dagger.Binds;
import dagger.Module;

/**
 * Created by marci on 2017-12-15.
 */
@Module
public abstract class ChooseEventIconModule {

    @Binds
    abstract ChooseEventIconContract.View bindView(ChooseEventIconDialogFragment view);

    @Binds
    abstract ChooseEventIconContract.Presenter bindPresenter(ChooseEventIconPresenter presenter);
}
