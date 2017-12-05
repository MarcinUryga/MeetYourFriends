package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_event_questionnaire.*
import org.joda.time.DateTime
import org.joda.time.chrono.ISOChronology

/**
 * Created by marci on 2017-12-05.
 */
class EventQuestionnaireFragment : BaseFragment<EventQuestionnaireContract.Presenter>(), EventQuestionnaireContract.View {

  var selectedDate: DateTime = DateTime().withChronology(ISOChronology.getInstance())

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_event_questionnaire, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dateSuggestionTextView.text = getString(R.string.your_date_suggestion, "${selectedDate.year}-${selectedDate.monthOfYear}-${selectedDate.dayOfMonth}")
    timeSuggestionTextView.text = getString(R.string.your_date_suggestion, "${selectedDate.hourOfDay}:${selectedDate.minuteOfHour}")
    setUpDateChooserButton()
    setUpTimeChooserButton()
    confirmDateSuggestionButton.setOnClickListener {
      Toast.makeText(context, "${selectedDate.year}-${selectedDate.monthOfYear}-${selectedDate.dayOfMonth} ${selectedDate.hourOfDay}:${selectedDate.minuteOfHour}", Toast.LENGTH_LONG).show()
    }
  }

  private fun setUpDateChooserButton() {
    dateChooserButton.setOnClickListener {
      DatePickerDialog(
          context,
          DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            selectedDate = DateTime(year, monthOfYear, dayOfMonth, selectedDate.hourOfDay, selectedDate.minuteOfHour)
            dateSuggestionTextView.text = getString(R.string.your_date_suggestion, selectedDate.toLocalDate().toString())
          },
          selectedDate.year,
          selectedDate.monthOfYear - 1,
          selectedDate.dayOfMonth
      ).show()
    }
  }

  private fun setUpTimeChooserButton() {
    timeChooserButton.setOnClickListener {
      TimePickerDialog(
          context,
          TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            selectedDate = DateTime(selectedDate.year, selectedDate.monthOfYear, selectedDate.dayOfMonth, hour, minute)
            timeSuggestionTextView.text = getString(R.string.your_date_suggestion, "${selectedDate.hourOfDay}:${selectedDate.minuteOfHour}")
          },
          selectedDate.hourOfDay,
          selectedDate.minuteOfHour,
          true
      ).show()
    }
  }
}