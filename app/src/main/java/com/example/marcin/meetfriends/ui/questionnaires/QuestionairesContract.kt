package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent

/**
 * Created by marci on 2017-12-12.
 */
interface QuestionairesContract {

  interface View : MvpView {

    fun manageEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>)
  }

  interface Presenter : MvpPresenter
}