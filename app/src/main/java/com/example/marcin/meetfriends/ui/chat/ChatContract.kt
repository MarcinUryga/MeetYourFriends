package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-21.
 */
interface ChatContract {

  interface View : MvpView {

    fun addMessage(chat: Chat)
  }

  interface Presenter : MvpPresenter {

    fun sendMessage(event: Event, text: String)
  }
}