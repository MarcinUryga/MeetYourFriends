package com.example.marcin.meetfriends.extensions

import com.beltaief.reactivefb.actions.ReactiveLogin
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import io.reactivex.Observable

/**
 * Created by MARCIN on 2017-11-13.
 */

fun LoginButton.reactiveLoginWithButton(): Observable<LoginResult> {
  return ReactiveLogin.loginWithButton(this)
}