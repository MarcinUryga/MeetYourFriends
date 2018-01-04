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

  override fun manageEvent(post: RxFirebaseChildEvent<DataSnapshot>) {
    eventsAdapter.manageChildItem(post)
    presenter.handleChosenEvent(eventsAdapter.getClickEvent())
  }

}