package com.example.marcin.meetfriends.ui.friends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.friends_item.view.*

/**
 * Created by marci on 2017-11-16.
 */
class FriendsAdapter(
    private val friendsList: List<User>
) : RecyclerView.Adapter<FriendsViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FriendsViewHolder.create(parent)

  override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) = holder.bind(friendsList[position])

  override fun getItemCount() = friendsList.size
}

class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  companion object {
    fun create(parent: ViewGroup): FriendsViewHolder {
      return FriendsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.friends_item, parent, false))
    }
  }

  fun bind(friend: User) {
    itemView.friendDisplayName.text = "${friend.firstName} ${friend.lastName}"
    Picasso.with(itemView.context).load(friend.photoUrl).into(itemView.friendImage)
  }
}