package com.example.marcin.meetfriends.ui.search_venues.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marci.googlemaps.pojo.Place
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.utils.LatLngConverter
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * Created by marci on 2017-12-27.
 */
class VenuesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(venue: Place) {
    itemView.placeNameTextView.text = venue.name
    itemView.placeRatingTextView.text = venue.rating.toString()
    itemView.openingHoursTextView.text = itemView.context.getString(R.string.opening_hours, LatLngConverter.coutnDistanceFromLatLng(49.767151, 20.4531756, venue.geometry.location.lat, venue.geometry.location.lng).toString())
  }

  companion object {
    fun create(parent: ViewGroup): VenuesViewHolder {
      return VenuesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false))
    }
  }
}