/*
package com.example.marcin.meetfriends.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

*/
/**
 * Created by marci on 2017-12-10.
 *//*

class UpdateDateTimeEventService : IntentService(this::class.toString()), UpdateDateTimeEventContract.Service {

  @Inject lateinit var presenter: UpdateDateTimeEventContract.Presenter

  override fun onCreate() {
    AndroidInjection.inject(this)
    super.onCreate()
  }

  override fun onHandleIntent(intent: Intent) {
    EventBasicInfoParams(intent.extras)
    Timber.d("ok")
    presenter.sendVote()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
  }

  companion object {
    fun newIntent(context: Context, eventBasicInfoParams: EventBasicInfoParams): Intent {
      val intent = Intent(context, UpdateDateTimeEventService::class.java)
      intent.putExtras(eventBasicInfoParams.data)
      return intent
    }
  }
}*/
