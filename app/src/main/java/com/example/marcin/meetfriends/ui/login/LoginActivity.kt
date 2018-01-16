package com.example.marcin.meetfriends.ui.login

import android.content.Context
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

  public override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    presenter.registerlogin(loginButton.reactiveLoginWithButton())
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    ReactiveLogin.onActivityResult(requestCode, resultCode, data)
  }

  override fun showProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun startMainActivity() {
    finish()
    startActivity(MainActivity.newIntent(this))
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, LoginActivity::class.java)
    }
  }
}
