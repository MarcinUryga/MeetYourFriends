package com.example.marcin.meetfriends.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.beltaief.reactivefb.actions.ReactiveLogin
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.reactiveLoginWithButton
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.main.MainActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity<LoginContract.Presenter>(), LoginContract.View {
/*  public override fun onStart() {
    super.onStart()
    // Check if user is signed in (non-null) and update UI accordingly.
    if (auth.currentUser != null) {
      val currentUser = auth.currentUser
//    updateUI(currentUser)
    }
  }*/

  public override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    presenter.registerlogin(loginButton.reactiveLoginWithButton())
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    // Pass the activity result back to the Facebook SDK
    ReactiveLogin.onActivityResult(requestCode, resultCode, data);
  }

  override fun showProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun startMainActivity() {
    startActivity(MainActivity.newIntent(this))
  }
}
