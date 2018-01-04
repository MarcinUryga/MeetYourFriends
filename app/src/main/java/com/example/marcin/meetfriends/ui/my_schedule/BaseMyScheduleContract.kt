package com.example.marcin.meetfriends.ui.my_schedule

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable

/**
 * Created by marci on 2018-01-03.
 */
interface BaseMyScheduleContract {

  interface View : MvpView {

    fun manageEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>)
  }

  interface Presenter : MvpPresenter {

    fun handleChosenEvent(clickEvent: Observable<Event>)
  }
}