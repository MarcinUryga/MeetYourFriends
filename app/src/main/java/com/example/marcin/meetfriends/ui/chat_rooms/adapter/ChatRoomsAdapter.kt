package com.example.marcin.meetfriends.ui.chat_rooms.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Event
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by marci on 2017-11-20.
 */
class ChatRoomsAdapter(
    private val eventsList: List<Event>
) : RecyclerView.Adapter<ChatRoomsViewHolder>() {

  private val publishSubject = PublishSubject.create<Event>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatRoomsViewHolder.create(parent)

  override fun onBindViewHolder(holder: ChatRoomsViewHolder, position: Int) {
    holder.bind(eventsList[position])
    holder.itemView.setOnClickListener {
      publishSubject.onNext(eventsList[position])
    }
  }

  override fun getItemCount() = eventsList.size

  fun getClickEvent(): Observable<Event> {
    return publishSubject
  }
}