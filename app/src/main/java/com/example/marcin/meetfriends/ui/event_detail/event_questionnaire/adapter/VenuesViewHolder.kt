package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.transformDistance
import com.example.marcin.meetfriends.models.FirebasePlace
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * Created by marci on 2017-12-31.
 */
class VenuesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(venue: FirebasePlace) {
    itemView.venueNameTextView.text = venue.name
    itemView.distanceTextView.text = venue.distance?.transformDistance(itemView.context)
    itemView.ratingTextView.text = venue.rating.toString()
    itemView.venueVicinityTextView.text = venue.vicinity
    Picasso.with(itemView.context).load(venue.photos.first()).placeholder(itemView.context.getDrawable(R.drawable.placeholder)).into(itemView.venueImage)
  }

  companion object {
    fun create(parent: ViewGroup): VenuesViewHolder {
      return VenuesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false))
    }
  }
}