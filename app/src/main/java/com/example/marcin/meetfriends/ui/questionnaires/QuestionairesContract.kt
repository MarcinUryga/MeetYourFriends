package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsContract

/**
 * Created by marci on 2017-12-12.
 */
interface QuestionairesContract {

  interface View : BaseLoadEventsContract.View {

    fun showLoadingProgressBar()

    fun startEventQuestionnaireFragment(eventBasicInfoParams: EventBasicInfoParams)
  }

  interface Presenter : BaseLoadEventsContract.Presenter
}