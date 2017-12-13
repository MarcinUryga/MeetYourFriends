package com.example.marcin.meetfriends.ui.chat_rooms

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.setEditTextHint
import com.example.marcin.meetfriends.extensions.setMargins
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.chat_rooms.adapter.ChatRoomsAdapter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.google.firebase.database.DataSnapshot
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_events_rooms.*

/**
 * Created by marci on 2017-11-20.
 */
class ChatRoomsFragment : BaseFragment<ChatRoomsContract.Presenter>(), ChatRoomsContract.View {

  private val postAdapter = ChatRoomsAdapter()

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_events_rooms, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    swipeRefreshLayout.setOnRefreshListener {
      presenter.onRefresh()
    }
  }

  override fun onResume() {
    super.onResume()
    eventRoomsRecyclerView.visibility = View.VISIBLE
    eventRoomsRecyclerView.layoutManager = LinearLayoutManager(context)
    eventRoomsRecyclerView.adapter = postAdapter
  }

  override fun showEmptyEvents() {
    emptyEventItemsLayout.visibility = View.VISIBLE
    noItemsImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chat_red_24dp))
    noItemsTextView.text = getString(R.string.you_do_not_have_any_events_yet)
  }

  override fun hideEmptyEvents() {
    emptyEventItemsLayout.visibility = View.INVISIBLE
  }

  override fun hideRefresh() {
    swipeRefreshLayout.isRefreshing = false
  }

  override fun showLoading() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun manageEvent(post: RxFirebaseChildEvent<DataSnapshot>) {
    postAdapter.manageChildItem(post)
    presenter.handleChosenChatRoomdEvent(postAdapter.getClickEvent())
  }

  override fun startChatRoomActivity(params: EventBasicInfoParams) {
    startActivity(ChatActivity.newIntent(context, params))
  }
}