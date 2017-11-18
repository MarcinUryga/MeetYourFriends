package com.example.marcin.meetfriends.ui.friends

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.friends.adapter.FriendsAdapter
import dagger.android.support.AndroidSupportInjection
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
    setActionBarTitle(getString(R.string.inite_friends))
  }

  override fun showFriendsList(friendsList: List<User>) {
    friendsAdapter = FriendsAdapter(friendsList)
    friendsRecyclerView.layoutManager = LinearLayoutManager(context)
    friendsRecyclerView.adapter = friendsAdapter
  }
}