package com.example.marcin.meetfriends.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.chat.adapter.ChatAdapter
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.utils.DateTimeFormatters
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_chat_room.*
import org.joda.time.DateTime

class ChatActivity : BaseActivity<ChatContract.Presenter>(), ChatContract.View {

  private val chatAdapter = ChatAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat_room)
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

  override fun addMessage(message: Message) {
    chatAdapter.addMessage(message)
    chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)

  }

  override fun tryToVoteOnEventDate(message: Message) {
    if (message.ifContainsDate()) {
      Toast.makeText(baseContext, "Hurrraaa!!! ${message.transformDateHandlerToJodaTime()}", Toast.LENGTH_SHORT).show()
      presenter.sendDateVote(message.transformDateHandlerToJodaTime())
    }
  }

  override fun showChosenDateSnackBar(selectedDate: DateTime, voter: Pair<String, String>) {
    Snackbar.make(
        this.snackBarContainer,
        getString(R.string.chosen_date, "${DateTimeFormatters.formatToShortDate(selectedDate)} ${DateTimeFormatters.formatToShortTime(selectedDate)}"),
        Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeChosenDateFromEvent(selectedDate, voter)
        }).show()
  }

  companion object {
    fun newIntent(context: Context, eventBasicInfoParams: EventBasicInfoParams): Intent {
      val intent = Intent(context, ChatActivity::class.java)
      intent.putExtras(eventBasicInfoParams.data)
      return intent
    }
  }
/*
  override fun startUpdateDateTimeService(params: EventBasicInfoParams) {
    startService(UpdateDateTimeEventService.newIntent(baseContext, params))
  }*/
}
