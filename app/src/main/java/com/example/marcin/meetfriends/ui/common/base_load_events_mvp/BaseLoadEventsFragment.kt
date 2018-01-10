/*
package com.example.marcin.meetfriends.ui.common.base_load_events_mvp

import android.view.View
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.my_schedule.adapter.PlannedEventsViewHolder
import com.google.firebase.database.DataSnapshot
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_available_events.*

*/
/**
 * Created by marci on 2018-01-04.
 *//*

abstract class BaseLoadEventsFragment<P : BaseLoadEventsContract.Presenter> : BaseFragment<P>(), BaseLoadEventsContract.View {

  abstract var eventsAdapter: RxFirebaseRecyclerAdapter<PlannedEventsViewHolder, Event>
//  private val eventsAdapter = PlannedEventsAdapter()

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
}*/
