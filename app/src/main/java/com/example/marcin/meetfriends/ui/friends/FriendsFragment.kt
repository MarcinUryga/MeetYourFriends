package com.example.marcin.meetfriends.ui.friends

import android.annotation.SuppressLint
import android.content.Context
import com.example.marcin.meetfriends.mvp.BaseFragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created by MARCIN on 2017-11-13.
 */
class FriendsFragment : BaseFragment<FriendsContract.Presenter>(), FriendsContract.View {

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }
}