package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.FirebasePlace

/**
 * Created by marci on 2017-12-31.
 */
class VenuesAdapter(
    private val venuesList: List<FirebasePlace>
) : RecyclerView.Adapter<VenuesViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VenuesViewHolder.create(parent)

  override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
    holder.bind(venuesList[position])
  }

  override fun getItemCount() = venuesList.size
}