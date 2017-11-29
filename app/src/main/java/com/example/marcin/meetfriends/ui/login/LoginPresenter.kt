package com.example.marcin.meetfriends.ui.login

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.utils.Constants
import com.facebook.AccessToken
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
@ScreenScope
class LoginPresenter @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

  override fun registerlogin(observableLoginResult: Observable<LoginResult>) {
    val disposableObserver = observableLoginResult
        .subscribe({ loginResult ->
          handleFacebookAccessToken(loginResult.accessToken)
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposableObserver)
  }

  private fun handleFacebookAccessToken(accesToken: AccessToken) {
    val credential = FacebookAuthProvider.getCredential(accesToken.token)
    val disposable = RxFirebaseAuth.signInWithCredential(auth, credential)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
        .doFinally { view.hideProgressBar() }
        .subscribe({ result ->
          view.startMainActivity()
          val user = User(
              uid = result.user.uid,
              facebookId = result.additionalUserInfo.profile["id"].toString(),
              displayName = result.user.displayName,
              photoUrl = result.user.photoUrl.toString(),
              phoneNumber = result.user.phoneNumber,
              email = result.user.email,
              firebaseToken = accesToken.token
          )
          saveUser(user)
        }, { error ->

        })
    disposables?.add(disposable)
  }

  private fun saveUser(user: User) {
    RxFirebaseDatabase
        .setValue(firebaseDatabase.reference.child(Constants.FIREBASE_USERS).child(user.uid), user)
        .doFinally { view.showToast("Data saved!") }.subscribe()
  }
}