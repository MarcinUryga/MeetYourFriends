package com.example.marcin.meetfriends.ui.my_schedule.confirmed_events

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.common.params.EventIdParams
import com.example.marcin.meetfriends.ui.confirmed_event_detail.ConfirmedEventActivity
import com.example.marcin.meetfriends.ui.my_schedule.adapter.PlannedEventsAdapter
import com.google.firebase.database.DataSnapshot
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.fragment_available_events.*

/**
 * Created by MARCIN on 2017-11-13.
 */

class ConfirmedEventsFragment : BaseFragment<ConfirmedEventsContract.Presenter>(), ConfirmedEventsContract.View {

  var eventsAdapter = PlannedEventsAdapter()

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onResume() {
    super.onResume()
    eventsRecyclerView.visibility = View.VISIBLE
    eventsRecyclerView.layoutManager = LinearLayoutManager(context)
    eventsRecyclerView.adapter = eventsAdapter
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_available_events, container, false)
  }

  override fun showNoEventsView() {
    noEventsLayout.visibility = View.VISIBLE
    progressBar.visibility = View.INVISIBLE
    noEventsTextView.text = getString(R.string.no_confirmed_events_yet)
  }

  override fun hideLoadingProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun hideNoEventsLayout() {
    noEventsLayout.visibility = View.INVISIBLE
  }

  override fun manageEvent(rxFirebaseChildEvent: RxFirebaseChildEvent<DataSnapshot>) {
    eventsAdapter.manageChildItem(rxFirebaseChildEvent)
    presenter.handleChosenEvent(eventsAdapter.getClickEvent())
  }

  override fun getEventItemsSizeFromAdapter() = eventsAdapter.itemCount

  override fun startConfirmedEventActivity(eventIdParams: EventIdParams) {
    startActivity(ConfirmedEventActivity.newIntent(context, eventIdParams))
  }
}