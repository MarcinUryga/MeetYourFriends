package com.example.marcin.meetfriends.ui.charts.adapter.voters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Voter
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_voter.view.*

/**
 * Created by marci on 2018-01-02.
 */
class VotersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(voter: Voter) {
    Picasso.with(itemView.context).load(voter.userPhotoUrl).transform(CircleTransform()).into(itemView.voterImage)
  }

  companion object {
    fun create(parent: ViewGroup): VotersViewHolder {
      return VotersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_voter, parent, false))
    }
  }
}