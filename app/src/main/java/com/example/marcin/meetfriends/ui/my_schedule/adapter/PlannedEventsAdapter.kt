package com.example.marcin.meetfriends.ui.my_schedule.adapter

import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Event
import durdinapps.rxfirebase2.RxFirebaseRecyclerAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * Created by marci on 2017-11-28.
 */
class PlannedEventsAdapter : RxFirebaseRecyclerAdapter<PlannedEventsViewHolder, Event>(Event::class.java) {

  private val publishSubject = PublishSubject.create<Event>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlannedEventsViewHolder.create(parent)

  override fun onBindViewHolder(holder: PlannedEventsViewHolder, position: Int) {
    holder.bind(items[position])
    holder.itemView.setOnClickListener {
      publishSubject.onNext(items[position])
    }
  }

  fun getClickEvent(): Observable<Event> {
    return publishSubject
  }

  override fun itemAdded(item: Event, key: String, position: Int) {
    //Add the refs if you need them later
//    item.setRef(key)
    Timber.d("Added a new item to the adapter.")
  }

  override fun itemChanged(oldItem: Event, newItem: Event, key: String, position: Int) {
    //Add the refs if you need them later
//    newItem.setRef(key)
    Timber.d("Changed an item.")
  }

  override fun itemRemoved(item: Event, key: String, position: Int) {
    Timber.d("Removed an item.")
  }

  override fun itemMoved(item: Event, key: String, oldPosition: Int, newPosition: Int) {
    Timber.d("Moved an item.")
  }
}