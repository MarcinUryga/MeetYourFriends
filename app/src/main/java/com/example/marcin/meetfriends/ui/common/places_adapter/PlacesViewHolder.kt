package com.example.marcin.meetfriends.ui.common.places_adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.transformDistance
import com.example.marcin.meetfriends.models.Place
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_place.view.*

/**
 * Created by marci on 2017-12-27.
 */
class PlacesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(venue: Place) {
    itemView.venueNameTextView.text = venue.name
    itemView.venueVicinityTextView.text = venue.vicinity
    itemView.distanceTextView.text = venue.distance?.transformDistance(itemView.context)
    if (venue.duration != null) {
      itemView.durationTextView.text = venue.duration.text
    } else {
      itemView.durationTextView.visibility = View.GONE
    }
    itemView.ratingTextView.text = venue.rating.toString()
    if (venue.photos != null) {
      Picasso.with(itemView.context).load(venue.getPhotosUrl().first()).placeholder(R.drawable.placeholder).fit().into(itemView.venueImage)
    } else {
      Picasso.with(itemView.context).load(venue.placeIcon).placeholder(R.drawable.placeholder).fit().into(itemView.venueImage)
    }
    if (!venue.isAdded) {
      itemView.actionButton.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_add))
    } else {
      itemView.actionButton.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_remove_circle))
    }
  }

  companion object {
    fun create(parent: ViewGroup): PlacesViewHolder {
      return PlacesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))
    }
  }
}