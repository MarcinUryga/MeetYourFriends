package com.example.marcin.meetfriends.ui.event_detail.adapter

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
    Timber.d("Added a new item to the adapter.")
    participantsList.add(p0)
    notifyDataSetChanged()
  }

  override fun itemMoved(p0: User?, p1: String?, p2: Int, p3: Int) {
    Timber.d("Added a new item to the adapter.")
  }

  override fun itemRemoved(p0: User, p1: String?, p2: Int) {
    Timber.d("Added a new item to the adapter.")
    participantsList.remove(p0)
    notifyItemRemoved(participantsList.indexOf(p0))
  }

}/*class ParticipantsAdapter : RecyclerView.Adapter<ParticipantsViewHolder>() {

  private var participantsList = mutableListOf<User>()

  fun addParticipant(user: User) {
    participantsList.add(user)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ParticipantsViewHolder.create(parent)

  override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
    holder.bind(participantsList[position])
  }

  override fun getItemCount() = participantsList.size
}*/