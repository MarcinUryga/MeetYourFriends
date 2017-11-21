package com.example.marcin.meetfriends.ui.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Chat

/**
 * Created by marci on 2017-11-21.
 */
class ChatAdapter : RecyclerView.Adapter<ChatViewHolder>() {

  private val chatMessagesList = mutableListOf<Chat>()

  fun addMessage(chat: Chat) {
    chatMessagesList.add(chat)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatViewHolder.create(parent)

  override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
    holder.bind(chatMessagesList[position])
  }

  override fun getItemCount() = chatMessagesList.size
}