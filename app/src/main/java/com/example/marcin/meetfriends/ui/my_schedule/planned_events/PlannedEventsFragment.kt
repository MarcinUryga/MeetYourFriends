package com.example.marcin.meetfriends.ui.my_schedule.planned_events

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.my_schedule.adapter.PlannedEventsAdapter
import com.example.marcin.meetfriends.ui.planned_event_detail.EventDetailActivity
import com.google.firebase.database.DataSnapshot
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.fragment_available_events.*

/**
 * Created by marci on 2018-01-04.
 */

class PlannedEventsFragment : BaseFragment<PlannedEventsContract.Presenter>(), PlannedEventsContract.View {

  private val eventsAdapter = PlannedEventsAdapter()

  @SuppressLint("CheckResult")
  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_available_events, container, false)
  }

  override fun onResume() {
    super.onResume()
    eventsRecyclerView.visibility = View.VISIBLE
    eventsRecyclerView.layoutManager = LinearLayoutManager(context)
    eventsRecyclerView.adapter = eventsAdapter
  }

  override fun showNoEventsView() {
    noEventsLayout.visibility = View.VISIBLE
    progressBar.visibility = View.INVISIBLE
    noEventsTextView.text = getString(R.string.no_planned_events_yet)
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

  override fun startEventDetailActivity(eventBasicInfoParams: EventBasicInfoParams) {
    startActivity(EventDetailActivity.newIntent(context, eventBasicInfoParams))
  }
}