package com.example.marcin.meetfriends.ui.chat_rooms.adapter

import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.ui.common.adapter.BaseViewHolder
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event_room.view.*

/**
 * Created by marci on 2017-11-20.
 */
class ChatRoomsViewHolder(itemView: View) : BaseViewHolder(itemView) {

  fun bind(event: Event) {
    Picasso.with(itemView.context).load(event.iconId.let { it!! }.toInt()).transform(CircleTransform()).into(itemView.eventIcon)
    itemView.eventName.text = event.name
    itemView.amountOfParticipants.text = itemView.context.getString(R.string.amount_of_participants, event.participants.size)
    itemView.eventRoomIconImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.circle_chat_icon_primary_color))
  }

  companion object {
    fun create(parent: ViewGroup): ChatRoomsViewHolder {
      return ChatRoomsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_room, parent, false))
    }
  }
}