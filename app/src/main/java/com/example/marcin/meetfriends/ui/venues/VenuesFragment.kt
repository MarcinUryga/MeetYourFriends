package com.example.marcin.meetfriends.ui.venues

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created by MARCIN on 2017-11-13.
 */
class VenuesFragment : BaseFragment<VenuesContract.Presenter>(), VenuesContract.View {

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_venues, container, false)
  }
}