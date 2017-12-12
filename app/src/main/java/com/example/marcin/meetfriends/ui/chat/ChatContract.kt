package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import org.joda.time.DateTime

/**
 * Created by marci on 2017-11-21.
 */
interface ChatContract {

  interface View : MvpView {

    fun addMessage(message: Message)

    fun tryToVoteOnEventDate(message: Message)

    fun showChosenDateSnackBar(selectedDate: DateTime, userId: String)

//    fun startUpdateDateTimeService(params: EventBasicInfoParams)
  }

  interface Presenter : MvpPresenter {

    fun sendMessage(text: String)

    fun sendDateVote(selectedDate: DateTime)

    fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String)
  }
}