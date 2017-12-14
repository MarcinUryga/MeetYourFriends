package com.example.marcin.meetfriends.ui.create_event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragmentDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_dialog_create_event.*

/**
 * Created by marci on 2017-11-27.
 */
class CreateEventDialogFragment : BaseFragmentDialog<CreateEventContract.Presenter>(), CreateEventContract.View {

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
    return inflater.inflate(R.layout.fragment_dialog_create_event, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    createEventButton.setOnClickListener {
      presenter.tryToCreateEvent()
    }
  }

  override fun validateEventName(): Boolean {
    eventNameInputLayout.error = null
    if (eventNameEditText.text.isEmpty()) {
      eventNameInputLayout.error = getString(R.string.empty_event_name)
      return false
    }
    return true
  }

  override fun validateEventDescription(): Boolean {
    eventDescriptionInputLayout.error = null
    if (eventDescriptionEditText.text.isEmpty()) {
      eventDescriptionInputLayout.error = getString(R.string.empty_event_description)
      return false
    }
    return true
  }

  override fun getEventName() = eventNameEditText.text.toString()

  override fun getEventDescription() = eventDescriptionEditText.text.toString()

  override fun dismissDialogFragment() {
    dismiss()
  }
}