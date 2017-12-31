package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import org.joda.time.DateTime

/**
 * Created by marci on 2017-12-05.
 */
interface EventQuestionnaireContract {

  interface View : MvpView {

    fun showChosenDateSnackBar(selectedDate: DateTime, userId: String)

    fun setupVenuesAdapter(venuesList: List<FirebasePlace>)

    fun showProgressBar()

    fun hideProgressBar()

    fun buildAlertMessageNoGps()

    fun getActivity(): Activity
  }

  interface Presenter : MvpPresenter {

    fun sendDateVote(selectedDate: DateTime)

    fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String)

    fun getCurrentLocation(activity: FragmentActivity?)
  }
}