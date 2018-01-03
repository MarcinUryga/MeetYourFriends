package com.example.marcin.meetfriends.ui.confirmed_event_detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_participant.view.*

/**
 * Created by marci on 2018-01-03.
 */
class ParticipantsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(participant: User) {
    itemView.participantName.text = participant.displayName
    Picasso.with(itemView.context).load(participant.photoUrl).placeholder(R.drawable.placeholder).transform(CircleTransform()).into(itemView.participantImage)
  }

  companion object {
    fun create(parent: ViewGroup): ParticipantsViewHolder {
      return ParticipantsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_participant, parent, false))
    }
  }
}