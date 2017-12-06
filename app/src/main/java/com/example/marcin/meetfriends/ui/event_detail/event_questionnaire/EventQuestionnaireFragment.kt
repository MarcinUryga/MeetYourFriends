package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.utils.DateTimeFormatters
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_event_questionnaire.*
import org.joda.time.DateTime
import org.joda.time.chrono.ISOChronology

/**
 * Created by marci on 2017-12-05.
 */
class EventQuestionnaireFragment : BaseFragment<EventQuestionnaireContract.Presenter>(), EventQuestionnaireContract.View {

  private var selectedDate: DateTime = DateTime().withChronology(ISOChronology.getInstance())

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_event_questionnaire, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dateSuggestionTextView.text = getString(R.string.your_date_suggestion, DateTimeFormatters.formatToShortDate(selectedDate))
    timeSuggestionTextView.text = getString(R.string.your_time_suggestion, DateTimeFormatters.formatToShortTime(selectedDate))
    setUpDateChooserButton()
    setUpTimeChooserButton()
    confirmDateSuggestionButton.setOnClickListener {
      presenter.sendDateVote(selectedDate)
    }
  }

  override fun showChosenDateSnackBar(selectedDate: DateTime, voter: Pair<String, String>) {
    Snackbar.make(
        this.snackBarContainer,
        getString(R.string.chosen_date, "${DateTimeFormatters.formatToShortDate(selectedDate)} ${DateTimeFormatters.formatToShortTime(selectedDate)}"),
        Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeChosenDateFromEvent(selectedDate, voter)
        }).show()
  }

  private fun setUpDateChooserButton() {
    dateChooserButton.setOnClickListener {
      DatePickerDialog(
          context,
          DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            selectedDate = DateTime(year, monthOfYear, dayOfMonth, selectedDate.hourOfDay, selectedDate.minuteOfHour, 0, 0)
            dateSuggestionTextView.text = getString(R.string.your_date_suggestion, DateTimeFormatters.formatToShortDate(selectedDate))
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
            selectedDate = DateTime(selectedDate.year, selectedDate.monthOfYear, selectedDate.dayOfMonth, hour, minute, 0, 0)
            timeSuggestionTextView.text = getString(R.string.your_time_suggestion, DateTimeFormatters.formatToShortTime(selectedDate))
          },
          selectedDate.hourOfDay,
          selectedDate.minuteOfHour,
          true
      ).show()
    }
  }
}