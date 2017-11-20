package com.example.marcin.meetfriends.ui.friends

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.friends.adapter.FriendsAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_friends.*


/**
 * Created by MARCIN on 2017-11-13.
 */
class FriendsFragment : BaseFragment<FriendsContract.Presenter>(), FriendsContract.View {

  lateinit var friendsAdapter: FriendsAdapter

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_friends, container, false)
  }

  override fun showInviteFriendsTitle() {
    setActionBarTitle(getString(R.string.invite_friends))
  }

  override fun showLoading() {
    progressBar.visibility = View.VISIBLE
  }

  override fun showFriendsList(friendsList: List<User>) {
    progressBar.visibility = View.INVISIBLE
    friendsAdapter = setUpFriendsAdapter(friendsList)
    friendsRecyclerView.layoutManager = LinearLayoutManager(context)
    friendsRecyclerView.adapter = friendsAdapter
  }

  override fun showCreateEventDialog() {
    val eventNameEditText = EditText(context)
    val parentLayout = LinearLayout(context)
    parentLayout.addView(eventNameEditText.setMargins(45, 45, 10, 10).setEditTextHint(getString(R.string.event_name)))
    AlertDialog.Builder(context)
        .setTitle(getString(R.string.create_new_event))
        .setMessage(getString(R.string.name_your_event))
        .setView(parentLayout)
        .setPositiveButton(android.R.string.yes, { _, _ ->
          val eventName = eventNameEditText.text.toString()
          if (eventName.isNotEmpty()) {
            presenter.createEvent(eventNameEditText.text.toString())
          }
        })
        .setNegativeButton(android.R.string.no, null)
        .show()
  }

  override fun showCreatedEventSnackBar(eventId: String) {
    Snackbar.make(activity.snackBarContainer, getString(R.string.created_new_event), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeEvent(eventId)
        }).show()
  }

  override fun showInvitedFriendSnackBar(friend: User, eventId: String) {
    Snackbar.make(activity.snackBarContainer, getString(R.string.added_friend, friend.displayName), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeFriendFromEvent(friend.uid.let { it!! }, eventId)
        }).show()
  }

  private fun setUpFriendsAdapter(friendsList: List<User>): FriendsAdapter {
    val adapter = FriendsAdapter(friendsList)
    presenter.handleInviteFriendEvent(adapter.getClickEvent())
    return adapter
  }
}