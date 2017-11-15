package com.example.marcin.meetfriends.ui.login

import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.facebook.login.LoginResult
import io.reactivex.Observable

/**
 * Created by marci on 2017-11-09.
 */
interface LoginContract {

  interface View : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun startMainActivity()

    fun showToast(msg: String)
  }

  interface Presenter : MvpPresenter {

    fun registerlogin(observableLoginRsult: Observable<LoginResult>)
  }
}