package com.example.marcin.meetfriends.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
abstract class BaseFragment<P : MvpPresenter> : Fragment() {

  @Inject lateinit var presenter: P

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
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
  }

  fun setActionBarTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = " $title"
  }
}