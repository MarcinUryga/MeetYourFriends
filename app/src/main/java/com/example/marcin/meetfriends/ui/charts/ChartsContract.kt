package com.example.marcin.meetfriends.ui.charts

import com.example.marcin.meetfriends.models.DateRow
import com.example.marcin.meetfriends.models.VenueRow
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2018-01-02.
 */
interface ChartsContract {

  interface View : MvpView {

    fun showDateQuestionnaireProgressBar()

    fun hideDateQuestionnaireProgressBar()

    fun showVenueQuestionnaireProgressBar()

    fun hideVenueQuestionnaireProgressBar()

    fun showDateQuestionnairesResult(dateRowsList: List<DateRow>)

    fun showVenueQuestionnairesResult(venueRowsList: List<VenueRow>)

    fun showNoDateVotes()

    fun showNoVenueVotes()
  }

  interface Presenter : MvpPresenter {

  }
}