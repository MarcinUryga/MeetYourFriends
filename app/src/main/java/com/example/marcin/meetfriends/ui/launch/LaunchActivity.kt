package com.example.marcin.meetfriends.ui.launch

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.main.MainActivity
import dagger.android.AndroidInjection


@SuppressLint("CheckResult")
class LaunchActivity : BaseActivity<LaunchContract.Presenter>(), LaunchContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
  }

  override fun startLoginAcivity() {
    startActivity(LoginActivity.newIntent(this))
  }

  override fun startMainActivity() {
    startActivity(MainActivity.newIntent(this))
  }
}
