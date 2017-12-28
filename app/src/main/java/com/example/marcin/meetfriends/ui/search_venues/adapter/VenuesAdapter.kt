package com.example.marcin.meetfriends.ui.search_venues.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place

/**
 * Created by marci on 2017-12-27.
 */
class VenuesAdapter : RecyclerView.Adapter<VenuesViewHolder>() {

  private var venues = listOf<Place>()

  fun createVenuesList(venues: List<Place>) {
    this.venues = venues
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VenuesViewHolder.create(parent)

  override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
    holder.bind(venues[position])
  }

  override fun getItemCount() = venues.size
}