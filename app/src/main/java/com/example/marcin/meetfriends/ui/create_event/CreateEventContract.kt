package com.example.marcin.meetfriends.ui.create_event

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by marci on 2017-11-27.
 */
interface CreateEventContract {

  interface View : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showMyEvents(events: List<Event>)

    fun showNoEventsTextView()

    fun hideNoEventsTextView()

    fun dismissDialogFragment()
  }

  interface Presenter : MvpPresenter {

    fun handleChosenEvent(observableEvent: Observable<Event>)
  }
}