package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-21.
 */
interface ChatContract {

  interface View : MvpView

  interface Presenter : MvpPresenter
}