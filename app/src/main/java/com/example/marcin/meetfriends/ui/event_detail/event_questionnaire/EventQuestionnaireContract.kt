package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import org.joda.time.DateTime

/**
 * Created by marci on 2017-12-05.
 */
interface EventQuestionnaireContract {

  interface View : MvpView {

    fun showChosenDateSnackBar(selectedDate: DateTime, voter: Pair<String, String>)
  }

  interface Presenter : MvpPresenter {

    fun sendDateVote(selectedDate: DateTime)

    fun removeChosenDateFromEvent(selectedDate: DateTime, voter: Pair<String, String>)
  }
}