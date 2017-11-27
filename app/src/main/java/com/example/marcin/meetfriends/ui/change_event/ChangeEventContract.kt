package com.example.marcin.meetfriends.ui.change_event

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by marci on 2017-11-27.
 */
interface ChangeEventContract {

  interface View : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showMyEvents(events: List<Event>)

    fun dismissDialogFragment()
  }

  interface Presenter : MvpPresenter {

    fun handleChosenEvent(observableEvent: Observable<Event>)
  }
}