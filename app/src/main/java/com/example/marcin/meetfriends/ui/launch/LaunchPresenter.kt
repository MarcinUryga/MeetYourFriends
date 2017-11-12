package com.example.marcin.meetfriends.ui.launch

import android.content.Context
import com.beltaief.reactivefb.ReactiveFB
import com.beltaief.reactivefb.SimpleFacebookConfiguration
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by marci on 2017-11-11.
 */
@ScreenScope
class LaunchPresenter @Inject constructor(
    private val configuration: SimpleFacebookConfiguration,
    private val context: Context
) : BasePresenter<LaunchContract.View>(), LaunchContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    ReactiveFB.sdkInitialize(context)
    ReactiveFB.setConfiguration(configuration)
  }
}