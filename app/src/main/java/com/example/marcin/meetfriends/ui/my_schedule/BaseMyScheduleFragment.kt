package com.example.marcin.meetfriends.ui.my_schedule

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.my_schedule.adapter.PlannedEventsAdapter
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.fragment_planned_events.*

/**
 * Created by marci on 2018-01-04.
 */
abstract class BaseMyScheduleFragment<P : BaseMyScheduleContract.Presenter> : BaseFragment<P>(), BaseMyScheduleContract.View {

  private val eventsAdapter = PlannedEventsAdapter()

  override fun onResume() {
    super.onResume()
    plannedEventsRecyclerView.visibility = View.VISIBLE
    plannedEventsRecyclerView.layoutManager = LinearLayoutManager(context)
    plannedEventsRecyclerView.adapter = eventsAdapter
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_planned_events, container, false)
  }

  override fun showLoadingProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoadingProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  abstract override fun showNoEventsView()

  override fun hideNoEventsLayout() {
    noEventsLayout.visibility = View.INVISIBLE
  }

  override fun getEventItemsSizeFromAdapter() = eventsAdapter.itemCount

  override fun manageEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    eventsAdapter.manageChildItem(dataSnapshot)
    presenter.handleChosenEvent(eventsAdapter.getClickEvent())
  }
}