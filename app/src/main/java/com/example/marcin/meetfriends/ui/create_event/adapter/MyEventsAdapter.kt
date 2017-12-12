package com.example.marcin.meetfriends.ui.create_event.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by marci on 2017-11-27.
 */
class MyEventsAdapter(private val events: List<Event>) : RecyclerView.Adapter<MyEventsViewHolder>() {

  private val publishSubject = PublishSubject.create<Event>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyEventsViewHolder.create(parent)

  override fun onBindViewHolder(holder: MyEventsViewHolder?, position: Int) {
    (holder as MyEventsViewHolder).bind(events[position])
    holder.itemView.setOnClickListener {
      publishSubject.onNext(events[position])
      it.isEnabled = false
    }
  }

  override fun getItemCount() = events.size

  fun getClickEvent(): Observable<Event> {
    return publishSubject
  }
}