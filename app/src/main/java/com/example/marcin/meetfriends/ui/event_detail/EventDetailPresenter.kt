package com.example.marcin.meetfriends.ui.event_detail

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by marci on 2017-11-28.
 */
@ScreenScope
class EventDetailPresenter @Inject constructor(

) : BasePresenter<EventDetailContract.View>(), EventDetailContract.Presenter {

}