package com.example.marcin.meetfriends.ui.launch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.login.LoginActivity
import dagger.android.AndroidInjection

@SuppressLint("CheckResult")
class LaunchActivity : BaseActivity<LaunchContract.Presenter>(), LaunchContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    startActivity(Intent(this, LoginActivity::class.java))
  }
}
