package com.example.marcin.meetfriends.ui.friends.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.ui.friends.viewmodel.Friend
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_friends.view.*

/**
 * Created by marci on 2017-11-16.
 */
class FriendsAdapter(
    private val friendsList: List<Friend>
) : RecyclerView.Adapter<FriendsViewHolder>() {

  private val publishSubject = PublishSubject.create<Friend>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FriendsViewHolder.create(parent)

  override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
    holder.bind(friendsList[position])
    holder.itemView.inviteButton.setOnClickListener {
      publishSubject.onNext(friendsList[position])
      it.isEnabled = false
    }
  }

  override fun getItemCount() = friendsList.size

  fun getClickEvent(): Observable<Friend> {
    return publishSubject
  }

}

