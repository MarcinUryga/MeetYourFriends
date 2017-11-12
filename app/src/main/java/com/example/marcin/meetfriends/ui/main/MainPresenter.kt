package com.example.marcin.meetfriends.ui.main

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
@ScreenScope
class MainPresenter @Inject constructor(
    private val auth: FirebaseAuth
) : BasePresenter<MainContract.View>(), MainContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    view.showWelcomeText(auth.currentUser?.email)
  }
}