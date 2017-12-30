package com.example.marcin.meetfriends.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import com.example.marcin.meetfriends.ui.common.adapter.BaseViewHolder
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_other_chat_message.view.*

/**
 * Created by marci on 2017-11-22.
 */
class OtherMessageChatViewHolder(itemView: View) : BaseViewHolder(itemView) {

  fun bind(message: Message) {
    itemView.messageTextView.text = message.message
    if (message.ifContainsDate()) {
      itemView.messageTextView.underlineDate(message.dateHandler.let { it!! })
    }
    Picasso.with(itemView.context)
        .load(message.user?.photoUrl)
        .placeholder(R.drawable.circle_chat_user_placeholder)
        .transform(CircleTransform())
        .into(itemView.userPhotoImageView)
  }

  companion object {
    fun create(parent: ViewGroup): OtherMessageChatViewHolder {
      return OtherMessageChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_other_chat_message, parent, false))
    }
  }
}