package com.example.marcin.meetfriends

import android.content.Context
import com.example.marcin.meetfriends.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


/**
 * Created by marci on 2017-11-09.
 */
class MeetFriendsApplication : DaggerApplication() {

  lateinit var refWatcher: RefWatcher

  companion object {
    fun getRefWatcher(context: Context): RefWatcher {
      return (context.applicationContext as MeetFriendsApplication).refWatcher
    }
  }

  override fun onCreate() {
    super.onCreate()
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    refWatcher = LeakCanary.install(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerApplicationComponent.builder().create(this)
  }

}