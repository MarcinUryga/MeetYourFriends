package com.example.marcin.meetfriends.ui.event_detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.User

/**
 * Created by marci on 2017-11-29.
 */
class ParticipantsAdapter(
        private val participantsList: List<User>
) : RecyclerView.Adapter<ParticipantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParticipantsViewHolder.create(parent)

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        holder.bind(participantsList[position])
    }

    override fun getItemCount() = participantsList.size
}