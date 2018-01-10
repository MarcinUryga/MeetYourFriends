package com.example.marcin.meetfriends.ui.chat_rooms

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.chat_rooms.adapter.ChatRoomsAdapter
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.google.firebase.database.DataSnapshot
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.fragment_events_rooms.*

/**
 * Created by marci on 2017-11-20.
 */
class ChatRoomsFragment : BaseFragment<ChatRoomsContract.Presenter>(), ChatRoomsContract.View {

  private val chatAdapter = ChatRoomsAdapter()

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_events_rooms, container, false)
  }

  override fun onResume() {
    super.onResume()
    eventRoomsRecyclerView.visibility = View.VISIBLE
    eventRoomsRecyclerView.layoutManager = LinearLayoutManager(context)
    eventRoomsRecyclerView.adapter = chatAdapter
  }

  override fun showNoEventsView() {
    progressBar.visibility = View.INVISIBLE
    emptyEventItemsLayout.visibility = View.VISIBLE
    noItemsImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chat_red_24dp))
    noItemsTextView.text = getString(R.string.you_do_not_have_any_events_yet)
  }

  override fun hideNoEventsLayout() {
    emptyEventItemsLayout.visibility = View.INVISIBLE
  }

  override fun hideLoadingProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun manageEvent(rxFirebaseChildEvent: RxFirebaseChildEvent<DataSnapshot>) {
    chatAdapter.manageChildItem(rxFirebaseChildEvent)
    presenter.handleChosenEvent(chatAdapter.getClickEvent())
  }

  override fun getEventItemsSizeFromAdapter() = chatAdapter.itemCount


  override fun startChatRoomActivity(params: EventBasicInfoParams) {
    startActivity(ChatActivity.newIntent(context, params))
  }
}