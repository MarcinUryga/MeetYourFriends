package com.example.marcin.meetfriends.ui.place_details

import android.os.Bundle
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import dagger.android.AndroidInjection

/**
 * Created by marci on 2017-12-29.
 */
class PlaceDetailsActivity : BaseActivity<PlaceDetailsContract.Presenter>(), PlaceDetailsContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_place_details)
  }
}