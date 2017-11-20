package com.example.marcin.meetfriends.ui.friends

import android.content.SharedPreferences
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.storage.SharedPref
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
@ScreenScope
class FriendsPresenter @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val getFriendsFromFacebook: GetFriendsFromFacebook,
    private val getFriendsFromFirebase: GetFriendsFromFirebase,
    private val auth: FirebaseAuth,
    sharedPreferences: SharedPreferences
) : BasePresenter<FriendsContract.View>(), FriendsContract.Presenter {

  private val compositeDisposable = CompositeDisposable()
  private val sharedPref = SharedPref(sharedPreferences)

  override fun onViewCreated() {
    super.onViewCreated()
    view.showInviteFriendsTitle()
    val disposable = getFriendsFromFacebook.getFriends()
        .subscribe({ profiles ->
          val disposable = getFriendsFromFirebase.get()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .doOnSubscribe { view.showLoading() }
              .subscribe({ users ->
                view.showFriendsList(
                    users.filter { user ->
                      profiles.any { it.id == user.facebookId }
                    }
                )
              })
          disposables?.add(disposable)
        })
    disposables?.add(disposable)
  }

  override fun handleInviteFriendEvent(observable: Observable<User>) {
    val disposable = observable.subscribe({ friend ->
      val chosenEventId = sharedPref.getChosenEvent()
      if (chosenEventId == Constants.EMPTY_VALUE) {
        view.showCreateEventDialog()
      } else {
        val disposable = RxFirebaseDatabase
            .setValue(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(chosenEventId)
                .child(Constants.FIREBASE_USERS)
                .child(friend.uid), friend)
            .doFinally { view.showInvitedFriendSnackBar(friend, chosenEventId) }
            .subscribe()
        compositeDisposable.add(disposable)
      }
    })
    compositeDisposable.add(disposable)
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
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(eventId).removeValue()
  }

  override fun removeFriendFromEvent(friendId: String, eventId: String) {
    firebaseDatabase.reference
        .child(Constants.FIREBASE_EVENTS)
        .child(eventId).child(Constants.FIREBASE_USERS)
        .child(friendId)
        .removeValue()
  }
}
