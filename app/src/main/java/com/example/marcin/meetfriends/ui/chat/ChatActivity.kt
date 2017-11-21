package com.example.marcin.meetfriends.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.utils.Constants
import dagger.android.AndroidInjection

class ChatActivity : BaseActivity<ChatContract.Presenter>(), ChatContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat_room)
    val event = intent.getSerializableExtra(Constants.CHAT_ROOM_EXTRA) as Event
    Toast.makeText(baseContext, event.name, Toast.LENGTH_SHORT).show()
  }

  companion object {
    fun newIntent(context: Context, event: Event): Intent {
      val intent = Intent(context, ChatActivity::class.java)
      intent.putExtra(Constants.CHAT_ROOM_EXTRA, event)
      return intent
    }
  }
}
