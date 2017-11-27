package com.example.marcin.meetfriends.ui.main

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import com.example.marcin.meetfriends.ui.change_event.GetMyEventsUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.example.marcin.meetfriends.utils.Constants.FIREBASE_EVENTS
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
@ScreenScope
class MainPresenter @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    sharedPreferences: SharedPreferences
) : BasePresenter<MainContract.View>(), MainContract.Presenter {

  private val sharedPref = SharedPref(sharedPreferences)

  override fun onViewCreated() {
    super.onViewCreated()
    view.setUpActionBar(auth.currentUser?.photoUrl.toString())
  }

  override fun addNewEvent() {
    view.showCreateEventDialog()
  }

  override fun createEvent(eventName: String) {
    val eventId = firebaseDatabase.reference.push().key
    val event = Event(
        id = eventId,
        organizerId = auth.uid,
        name = eventName
    )
    val disposable = RxFirebaseDatabase
        .setValue(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(eventId), event)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {
          view.showCreatedEventSnackBar(eventId)
          sharedPref.saveChosenEvent(eventId)
        }
        .subscribe()
    disposables?.add(disposable)
  }

  override fun removeEvent(eventId: String) {
    firebaseDatabase.reference.child(FIREBASE_EVENTS).child(eventId).removeValue()
  }

  override fun changeEvent() {
    view.showChangeEventDialog()
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