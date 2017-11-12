package com.example.marcin.meetfriends.ui.login

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.facebook.AccessToken
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseAuth
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
    private val auth: FirebaseAuth
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

  private fun handleFacebookAccessToken(token: AccessToken) {
    val credential = FacebookAuthProvider.getCredential(token.token)
    RxFirebaseAuth.signInWithCredential(auth, credential)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showProgressBar() }
        .doFinally { view.hideProgressBar() }
        .subscribe({ result ->
          view.startMainActivity()
        }, { error ->

        })
  }
}