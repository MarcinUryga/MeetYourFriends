package com.example.marcin.meetfriends.ui.chat.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import com.example.marcin.meetfriends.ui.common.adapter.BaseViewHolder

/**
 * Created by marci on 2017-11-21.
 */
class ChatAdapter : RecyclerView.Adapter<BaseViewHolder>() {

  private val chatMessagesList = mutableListOf<Message>()

  companion object {
    private val VIEW_MY_MESSAGE = 0
    private val VIEW_OTHER_MESSAGE = 1
  }

  fun addMessage(message: Message) {
    chatMessagesList.add(message)
    chatMessagesList.sortBy { it.timestamp }
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    return when (viewType) {
      VIEW_MY_MESSAGE -> MyMessageChatViewHolder.create(parent)
      VIEW_OTHER_MESSAGE -> OtherMessageChatViewHolder.create(parent)
      else -> BaseViewHolder.empty(parent)
    }
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    when (holder.itemViewType) {
      VIEW_MY_MESSAGE -> (holder as? MyMessageChatViewHolder)?.bind(chatMessagesList[position])
      VIEW_OTHER_MESSAGE -> (holder as? OtherMessageChatViewHolder)?.bind(chatMessagesList[position])
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (chatMessagesList[position].ifMine) VIEW_MY_MESSAGE else VIEW_OTHER_MESSAGE
  }

  override fun getItemCount() = chatMessagesList.size
}

