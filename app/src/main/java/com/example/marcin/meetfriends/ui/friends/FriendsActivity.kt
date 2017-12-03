package com.example.marcin.meetfriends.ui.friends

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.friends.adapter.FriendsAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_friends.*

/**
 * Created by MARCIN on 2017-11-13.
 */
class FriendsActivity : BaseActivity<FriendsContract.Presenter>(), FriendsContract.View {

  private lateinit var friendsAdapter: FriendsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_friends)
    friendsRecyclerView.layoutManager = LinearLayoutManager(this)
  }

  override fun showLoading() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun showFriendsList(friendsList: List<User>) {
    friendsAdapter = setUpFriendsAdapter(friendsList)
    friendsRecyclerView.adapter = friendsAdapter
  }

  override fun showInvitedFriendSnackBar(friend: User, eventId: String) {
    Snackbar.make(this.snackBarContainer, getString(R.string.added_friend, friend.displayName), Snackbar.LENGTH_LONG)
        .setAction(getString(R.string.undo), {
          presenter.removeParticipantFromEvent(friend.uid.let { it!! }, eventId)
        }).show()
  }

  private fun setUpFriendsAdapter(friendsList: List<User>): FriendsAdapter {
    val adapter = FriendsAdapter(friendsList)
    presenter.handleInviteFriendEvent(adapter.getClickEvent())
    return adapter
  }

  companion object {
    fun newIntent(context: Context, params: EventIdParams): Intent {
      val intent = Intent(context, FriendsActivity::class.java)
      intent.putExtras(params.data)
      return intent
    }
  }
}