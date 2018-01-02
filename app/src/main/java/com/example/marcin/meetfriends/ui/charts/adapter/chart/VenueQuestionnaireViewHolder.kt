package com.example.marcin.meetfriends.ui.charts.adapter.chart

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.VenueRow
import com.example.marcin.meetfriends.ui.charts.adapter.voters.VotersAdapter
import com.example.marcin.meetfriends.ui.common.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_venue_chart_row.view.*

/**
 * Created by marci on 2018-01-02.
 */
class VenueQuestionnaireViewHolder(itemView: View) : BaseViewHolder(itemView) {

  fun bind(venueRow: VenueRow) {
    itemView.venueName.text = venueRow.venue.name
    val votersList = venueRow.voters
    val votersAdapter = VotersAdapter(votersList)
    itemView.votersRecyclerView.layoutManager = GridLayoutManager(itemView.context, 4)
    itemView.votersRecyclerView.adapter = votersAdapter
  }

  companion object {
    fun create(parent: ViewGroup): VenueQuestionnaireViewHolder {
      return VenueQuestionnaireViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue_chart_row, parent, false))
    }
  }
}