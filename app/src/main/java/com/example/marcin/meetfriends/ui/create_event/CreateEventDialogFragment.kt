package com.example.marcin.meetfriends.ui.create_event

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragmentDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_dialog_create_event.*


/**
 * Created by marci on 2017-11-27.
 */
class CreateEventDialogFragment : BaseFragmentDialog<CreateEventContract.Presenter>(), CreateEventContract.View {

  private var eventNameErrorMessage: String? = null
  private var eventDescriptionErrorMessage: String? = null

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_dialog_create_event, container, false)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setTitle("Create new event!")
    val textView = dialog.findViewById<View>(android.R.id.title) as TextView
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    textView.setPadding(10, 20, 10, 20)
    return dialog
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

  override fun showCreatedEventSnackBar(eventId: String) {
    Snackbar.make(snackBarContainer, getString(R.string.created_new_event), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeEvent(eventId)
        }).show()
  }

  override fun dismissDialogFragment() {
    dismiss()
  }
}