package com.example.marcin.meetfriends.ui.launch

import android.os.Bundle
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.login.LoginActivity
import com.example.marcin.meetfriends.ui.main.MainActivity
import dagger.android.AndroidInjection

class LaunchActivity : BaseActivity<LaunchContract.Presenter>(), LaunchContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.launch_activity)
  }

  override fun startLoginAcivity() {
    finish()
    startActivity(LoginActivity.newIntent(this))
  }

  override fun startMainActivity() {
    finish()
    startActivity(MainActivity.newIntent(this))
  }
}
