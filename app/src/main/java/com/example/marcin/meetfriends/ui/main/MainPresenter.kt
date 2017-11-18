package com.example.marcin.meetfriends.ui.main

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
@ScreenScope
class MainPresenter @Inject constructor(
    private val auth: FirebaseAuth
) : BasePresenter<MainContract.View>(), MainContract.Presenter {

  override fun logout() {
    LoginManager.getInstance().logOut()
    auth.signOut()
    view.startLoginActivity()
  }

}