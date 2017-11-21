package com.example.marcin.meetfriends.ui.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_my_chat_message.view.*

/**
 * Created by marci on 2017-11-21.
 */
class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(chat: Chat) {
    itemView.messageTextView.text = chat.message
    Picasso.with(itemView.context)
        .load(chat.user?.photoUrl)
        .placeholder(R.drawable.circle_chat_user_placeholder)
        .transform(CircleTransform())
        .into(itemView.userPhotoImageView)
  }

  companion object {
    fun create(parent: ViewGroup): ChatViewHolder {
      return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_chat_message, parent, false))
    }
  }
}