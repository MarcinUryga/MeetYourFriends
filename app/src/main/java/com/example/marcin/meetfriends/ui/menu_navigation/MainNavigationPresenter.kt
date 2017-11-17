package com.example.marcin.meetfriends.ui.menu_navigation

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * Created by marci on 2017-11-15.
 */
@ScreenScope
class MainNavigationPresenter @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : BasePresenter<MainNavigationContract.View>(), MainNavigationContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    val user = User(
        uid = auth.currentUser?.uid,
        displayName = auth.currentUser?.displayName,
        phoneNumber = auth.currentUser?.phoneNumber,
        photoUrl = auth.currentUser?.photoUrl.toString(),
        email = auth.currentUser?.email
    )
    view.setNavigationHeader(user)
  }
}