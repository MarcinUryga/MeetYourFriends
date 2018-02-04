package com.example.marcin.meetfriends.mvp

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.marcin.meetfriends.MeetFriendsApplication
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
abstract class BaseFragmentDialog<P : MvpPresenter> : DialogFragment() {

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

  override fun dismiss() {
//    super.dismiss()
    dialog.dismiss()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.destroy()
//    MeetFriendsApplication.getRefWatcher(activity).watch(this)

  }

  fun setActionBarTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = " $title"
  }
}