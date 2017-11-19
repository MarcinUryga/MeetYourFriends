package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.utils.Constants
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
    private val getFriendsFromFirebase: GetFriendsFromFirebase
) : BasePresenter<FriendsContract.View>(), FriendsContract.Presenter {

  private val compositeDisposable = CompositeDisposable()

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

  override fun handleEvent(observable: Observable<User>) {
    val disposable = observable.subscribe({ friend ->
      val disposable = RxFirebaseDatabase
          .setValue(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(firebaseDatabase.reference.push().key)
              .child(Constants.FIREBASE_USERS), friend)
          .doFinally { view.showToast(friend.displayName.toString()) }
          .subscribe()
      compositeDisposable.add(disposable)
    })
    compositeDisposable.add(disposable)
  }
}
