package com.example.marcin.meetfriends.ui.confirmed_event_detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.User

/**
 * Created by marci on 2018-01-03.
 */
class ParticipantsAdapter : RecyclerView.Adapter<ParticipantsViewHolder>() {

  private var participantsList = listOf<User>()

  fun initParticipantsList(participantsList: List<User>) {
    this.participantsList = participantsList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParticipantsViewHolder.create(parent)

  override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) = holder.bind(participantsList[position])

  override fun getItemCount() = participantsList.size
}