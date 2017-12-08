package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message

/**
 * Created by marci on 2017-11-21.
 */
interface ChatContract {

  interface View : MvpView {

    fun addMessage(message: Message)
  }

  interface Presenter : MvpPresenter {

    fun sendMessage(text: String)
  }
}