package com.example.marcin.meetfriends.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.marcin.meetfriends.MeetFriendsApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by marci on 2017-11-09.
 */
abstract class BaseActivity<P : MvpPresenter> : AppCompatActivity(), HasSupportFragmentInjector {

  @Inject lateinit var presenter: P
  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    presenter.onViewCreated()
  }

  override fun onStart() {
    super.onStart()
    presenter.start()
  }

  override fun onResume() {
    super.onResume()
    presenter.resume()
  }

  override fun onPause() {
    super.onPause()
    presenter.pause()
  }

  override fun onStop() {
    super.onStop()
    presenter.stop()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
//    MeetFriendsApplication.getRefWatcher(this).watch(this)
  }

  override fun supportFragmentInjector() = fragmentInjector
}