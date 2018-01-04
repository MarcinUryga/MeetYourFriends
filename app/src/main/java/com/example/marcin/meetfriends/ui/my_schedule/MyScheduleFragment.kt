package com.example.marcin.meetfriends.ui.my_schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.ui.my_schedule.confirmed_events.ConfirmedEventsFragment
import com.example.marcin.meetfriends.ui.my_schedule.planned_events.PlannedEventsFragment
import kotlinx.android.synthetic.main.fragment_my_schedule.*

/**
 * Created by MARCIN on 2017-11-13.
 */
class MyScheduleFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_my_schedule, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    eventsViewPager.adapter = PageAdapter(childFragmentManager)
    tabs.setupWithViewPager(eventsViewPager)
  }

  inner class PageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val tabTitles = arrayOf(getString(R.string.confirmed_event), getString(R.string.planned_events))
    private val pageCount = tabTitles.size

    override fun getItem(position: Int): Fragment {
      when (position) {
        0 -> return ConfirmedEventsFragment()
        1 -> return PlannedEventsFragment()
      }
      return ConfirmedEventsFragment()
    }

    override fun getPageTitle(position: Int): CharSequence {
      return tabTitles[position]
    }

    override fun getCount() = pageCount
  }
}