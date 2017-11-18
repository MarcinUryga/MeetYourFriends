package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
@ScreenScope
class FriendsPresenter @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val getFriendsFromFacebook: GetFriendsFromFacebook,
    private val getFriendsDromFirebase: GetFriendsFromFirebase
) : BasePresenter<FriendsContract.View>(), FriendsContract.Presenter {

  private val compositeDisposable = CompositeDisposable()

  override fun onViewCreated() {
    super.onViewCreated()
    view.showInviteFriendsTitle()
    /*getFriendsDromFirebase.get()
        .mergeWith { getFriendsFromFacebook.getFriends() }
        .subscribe({ users, profiles ->

          Timber.d(users.toString())
        }, { error ->
          Timber.e(error.localizedMessage)
        })*/
    getFriendsFromFacebook.getFriends()
        .subscribe({ profiles ->
          val friendsList = profiles.map {
            User(
                uid = it.id,
                displayName = it.name,
                photoUrl = it.getProfilePictureUri(170, 170).toString()
            )
          }
          view.showFriendsList(friendsList)
        })

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
