package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import io.reactivex.Observable

/**
 * Created by marci on 2017-12-12.
 */
interface QuestionairesContract {

  interface View : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showEmptyQuestionnairesToFillScreen()

    fun manageEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>)

    fun hideEmptyQuestionnairesToFillScreen()

    fun getEventItemsSizeFromAdapter(): Int

    fun startEventQuestionnaireFragment(eventBasicInfoParams: EventBasicInfoParams)
  }

  interface Presenter : MvpPresenter {

    fun handleChosenEventRoom(chosenEventRoom: Observable<Event>) {}
  }
}