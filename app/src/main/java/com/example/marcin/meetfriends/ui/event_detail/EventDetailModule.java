package com.example.marcin.meetfriends.ui.event_detail;

import com.example.marcin.meetfriends.ui.common.EventIdParams;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marci on 2017-11-28.
 */
@Module
public abstract class EventDetailModule {

    @Binds
    public abstract EventDetailContract.View bindView(EventDetailActivity view);

    @Binds
    public abstract EventDetailContract.Presenter bindPresenter(EventDetailPresenter presenter);

    @Provides
    static EventIdParams provideParams(EventDetailActivity activity) {
        return new EventIdParams(activity.getIntent().getExtras());
    }
}