package com.example.marcin.meetfriends.ui.create_event

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BaseFragmentDialog
import com.example.marcin.meetfriends.ui.create_event.adapter.MyEventsAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_dialog.*

/**
 * Created by marci on 2017-11-27.
 */
class CreateEventDialogFragment : BaseFragmentDialog<CreateEventContract.Presenter>(), CreateEventContract.View {

  lateinit var myEventsAdapter: MyEventsAdapter

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_dialog, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    eventsRecyclerView.layoutManager = LinearLayoutManager(context)
  }

  override fun showLoading() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun showNoEventsTextView() {
    noEventsTextView.visibility = View.VISIBLE
  }

  override fun hideNoEventsTextView() {
    noEventsTextView.visibility = View.INVISIBLE
  }

  override fun showMyEvents(events: List<Event>) {
    eventsLayout.visibility = View.VISIBLE
    prepareDialogFragmentTitle(events.size)
    myEventsAdapter = setupAdapter(events)
    eventsRecyclerView.adapter = myEventsAdapter
  }

  private fun prepareDialogFragmentTitle(amountOfEvents: Int) {
    chooseEventTextView.text = getString(
        R.string.you_organize_events,
        resources.getQuantityString(R.plurals.events, amountOfEvents, amountOfEvents)
    )
  }

  private fun setupAdapter(events: List<Event>): MyEventsAdapter {
    val adapter = MyEventsAdapter(events)
    presenter.handleChosenEvent(adapter.getClickEvent())
    return adapter
  }

  override fun dismissDialogFragment() {
    dismiss()
  }
}