package com.example.marcin.meetfriends.ui.common.base_load_events_mvp

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable

/**
 * Created by marci on 2018-01-03.
 */
interface BaseLoadEventsContract {

  interface View : MvpView {

    fun getEventItemsSizeFromAdapter(): Int

    fun hideLoadingProgressBar()

    fun showNoEventsView()

    fun manageEvent(rxFirebaseChildEvent: RxFirebaseChildEvent<DataSnapshot>)

    fun hideNoEventsLayout()
  }

  interface Presenter : MvpPresenter {

    fun handleChosenEvent(clickEvent: Observable<Event>)
  }
}