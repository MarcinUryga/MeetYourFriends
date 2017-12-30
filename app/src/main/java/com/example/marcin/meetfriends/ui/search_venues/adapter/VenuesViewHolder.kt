package com.example.marcin.meetfriends.ui.search_venues.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * Created by marci on 2017-12-27.
 */
class VenuesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(venue: Place) {
    itemView.placeNameTextView.text = venue.name
    itemView.placeVicinityTextView.text = venue.vicinity
    itemView.distanceTextView.text = venue.transformDistance(itemView.context)
    itemView.durationTextView.text = venue.duration.text
    itemView.ratingTextView.text = venue.rating.toString()
    if (venue.photos != null)
      Picasso.with(itemView.context).load(venue.getPhotosUrl().first()).placeholder(R.drawable.placeholder).into(itemView.placeImage)
    if (!venue.isAdded) {
      itemView.actionButton.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_add))
    } else {
      itemView.actionButton.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_remove_circle))
    }
  }

  companion object {
    fun create(parent: ViewGroup): VenuesViewHolder {
      return VenuesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false))
    }
  }
}