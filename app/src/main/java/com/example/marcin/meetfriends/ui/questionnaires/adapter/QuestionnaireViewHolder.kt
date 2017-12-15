package com.example.marcin.meetfriends.ui.questionnaires.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event_room.view.*

/**
 * Created by marci on 2017-12-12.
 */
class QuestionnaireViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(event: Event) {
    Picasso.with(itemView.context).load(event.iconId.let { it!! }.toInt()).transform(CircleTransform()).into(itemView.eventIcon)
    itemView.eventName.text = event.name
    itemView.amountOfParticipants.text = itemView.context.getString(R.string.amount_of_participants, event.participants.size)
    itemView.eventRoomIconImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.circle_charts_icon))
  }

  companion object {
    fun create(parent: ViewGroup): QuestionnaireViewHolder {
      return QuestionnaireViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_room, parent, false))
    }
  }
}