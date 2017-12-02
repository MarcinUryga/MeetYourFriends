package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable

/**
 * Created by MARCIN on 2017-11-13.
 */
interface PlannedEventsContract {

    interface View : MvpView {

        /* fun showEmptyEvents()

         fun hideEmptyEvents()

         fun hideRefresh()

         fun showLoading()

         fun hideLoading()
     */
        fun manageEvent(post: RxFirebaseChildEvent<DataSnapshot>)

        fun startEventDetailActivity(params: EventIdParams)
    }

    interface Presenter : MvpPresenter {

        fun handleChosenEvent(eventChatRoom: Observable<Event>)

    }
}
