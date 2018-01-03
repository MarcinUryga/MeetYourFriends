package com.example.marcin.meetfriends.ui.planned_event_detail.adapter

import android.view.ViewGroup
import com.example.marcin.meetfriends.models.User
import durdinapps.rxfirebase2.RxFirebaseRecyclerAdapter
import timber.log.Timber

/**
 * Created by marci on 2017-11-29.
 */
class ParticipantsAdapter : RxFirebaseRecyclerAdapter<ParticipantsViewHolder, User>(User::class.java) {

  private var participantsList = mutableListOf<User>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParticipantsViewHolder.create(parent)

  override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
    holder.bind(participantsList[position])
  }

  override fun getItemCount() = participantsList.size

  override fun itemChanged(p0: User?, p1: User?, p2: String?, p3: Int) {
    Timber.d("Added a new item to the adapter.")
  }

  override fun itemAdded(p0: User, p1: String?, p2: Int) {
    participantsList.add(p0)
    notifyDataSetChanged()
  }

  override fun itemMoved(p0: User?, p1: String?, p2: Int, p3: Int) {
    Timber.d("Added a new item to the adapter.")
  }

  override fun itemRemoved(p0: User, p1: String?, p2: Int) {
    val position = participantsList.indexOf(p0)
    participantsList.removeAt(position)
    notifyItemRemoved(position)
  }

  fun getParticipantsList() = participantsList
}