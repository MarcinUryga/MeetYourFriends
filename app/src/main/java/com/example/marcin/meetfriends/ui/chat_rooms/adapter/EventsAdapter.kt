package com.example.marcin.meetfriends.ui.chat_rooms.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_friends.view.*

/**
 * Created by marci on 2017-11-20.
 */
class EventsAdapter(
    private val eventsList: List<Event>
) : RecyclerView.Adapter<EventsViewHolder>() {

  private val publishSubject = PublishSubject.create<Event>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventsViewHolder.create(parent)

  override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
    holder.bind(eventsList[position])
   /* holder.itemView.inviteButton.setOnClickListener {
      publishSubject.onNext(eventsList[position])
//      it.isEnabled = false
    }*/
  }

  override fun getItemCount() = eventsList.size

  fun getClickEvent(): Observable<Event> {
    return publishSubject
  }
}