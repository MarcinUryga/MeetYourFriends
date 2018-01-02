package com.example.marcin.meetfriends.ui.charts.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.ChartRow
import com.example.marcin.meetfriends.utils.DateTimeFormatters
import kotlinx.android.synthetic.main.item_chart_row.view.*
import org.joda.time.DateTime

/**
 * Created by marci on 2018-01-02.
 */
class DateQuestionnaireViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(chartRow: ChartRow) {
    itemView.dateVote.text = DateTimeFormatters.formatToShortTimeDate(DateTime(chartRow.timestamp.toLong()))
    val votersList = chartRow.voters
    val votersAdapter = VotersAdapter(votersList)
    itemView.votersRecyclerView.layoutManager = GridLayoutManager(itemView.context, 4)
    itemView.votersRecyclerView.adapter = votersAdapter
  }

  companion object {
    fun create(parent: ViewGroup): DateQuestionnaireViewHolder {
      return DateQuestionnaireViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chart_row, parent, false))
    }
  }
}