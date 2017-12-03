package com.example.marcin.meetfriends.ui.friends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.ui.friends.viewmodel.Friend
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_friends.view.*

/**
 * Created by marci on 2017-11-18.
 */
class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  companion object {
    fun create(parent: ViewGroup): FriendsViewHolder {
      return FriendsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friends, parent, false))
    }
  }

  fun bind(friend: Friend) {
    itemView.friendDisplayName.text = friend.displayName
    Picasso.with(itemView.context).load(friend.photoUrl).transform(CircleTransform()).into(itemView.friendImage)
    /* if (friend.phoneNumber != null) {
       itemView.friendPhoneNumber.text = itemView.context.getString(R.string.phone, friend.phoneNumber)
     } else {*/
    itemView.friendPhoneNumber.visibility = View.GONE
//    }
  /*  if (friend.email != null) {
      itemView.friendEmail.text = itemView.context.getString(R.string.email, friend.email)
    } else {*/
      itemView.friendEmail.visibility = View.GONE
//    }
    itemView.inviteButton.isEnabled = !friend.isInvitated
  }
}