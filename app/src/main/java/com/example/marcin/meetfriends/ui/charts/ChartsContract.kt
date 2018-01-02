package com.example.marcin.meetfriends.ui.charts

import com.example.marcin.meetfriends.models.ChartRow
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2018-01-02.
 */
interface ChartsContract {

  interface View : MvpView {

    fun showQuestionnairesResult(chartRowsList: List<ChartRow>)
  }

  interface Presenter : MvpPresenter {

  }
}