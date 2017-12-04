package com.example.marcin.meetfriends.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.chat.adapter.ChatAdapter
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.utils.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatActivity : BaseActivity<ChatContract.Presenter>(), ChatContract.View {

  private val chatAdapter = ChatAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat_room)
    presenter.getMessages()
    sendMessageButton.setOnClickListener {
      presenter.sendMessage(inputMessageEditText.text.toString())
      inputMessageEditText.text = null
    }
    setUpRecyclerView()
  }

  private fun setUpRecyclerView() {
    chatRecyclerView.layoutManager = LinearLayoutManager(this)
    chatRecyclerView.adapter = chatAdapter
  }

  override fun addMessage(chat: Chat) {
    chatAdapter.addMessage(chat)
    chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
  }

  companion object {
    fun newIntent(context: Context, params: EventIdParams): Intent {
      val intent = Intent(context, ChatActivity::class.java)
      intent.putExtras(params.data)
      return intent
    }
  }
}
