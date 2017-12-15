package com.example.marcin.meetfriends.ui.my_schedule.planned_event

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.EventDetailActivity
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.PlannedEventsContract
import com.example.marcin.meetfriends.ui.my_schedule.planned_event.adapter.PlannedEventsAdapter
import com.google.firebase.database.DataSnapshot
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.fragment_planned_events.*

/**
 * Created by MARCIN on 2017-11-13.
 */
class PlannedEventsFragment : BaseFragment<PlannedEventsContract.Presenter>(), PlannedEventsContract.View {

  private val eventsAdapter = PlannedEventsAdapter()

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

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

  override fun startEventDetailActivity(params: EventBasicInfoParams) {
    startActivity(EventDetailActivity.newIntent(context, params))
  }
}