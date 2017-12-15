package com.example.marcin.meetfriends.ui.main

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
@ScreenScope
class MainPresenter @Inject constructor(
    private val auth: FirebaseAuth,
    sharedPreferences: SharedPreferences
) : BasePresenter<MainContract.View>(), MainContract.Presenter {

  private val sharedPref = SharedPref(sharedPreferences)

  override fun onViewCreated() {
    super.onViewCreated()
    view.setUpActionBar(auth.currentUser?.photoUrl.toString())
  }

  override fun addNewEvent() {
    view.startCreateEventActivity()
  }

  override fun tryLogout() {
    view.showConfirmLogoutDialog()
  }

  override fun logout() {
    LoginManager.getInstance().logOut()
    sharedPref.clearSharedPref()
    auth.signOut()
    view.startLoginActivity()
  }
}