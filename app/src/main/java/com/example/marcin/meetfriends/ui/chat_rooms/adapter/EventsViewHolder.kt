package com.example.marcin.meetfriends.ui.chat_rooms.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import kotlinx.android.synthetic.main.item_chat_room.view.*

/**
 * Created by marci on 2017-11-20.
 */
class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(event: Event) {
    itemView.eventName.text = event.name
    itemView.amountOfParticipants.text = itemView.context.getString(R.string.amount_of_participants, event.users?.size)
  }

  companion object {
    fun create(parent: ViewGroup): EventsViewHolder {
      return EventsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat_room, parent, false))
    }
  }
}