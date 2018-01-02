package com.example.marcin.meetfriends.ui.charts.adapter.chart

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.DateRow
import com.example.marcin.meetfriends.ui.charts.adapter.voters.VotersAdapter
import com.example.marcin.meetfriends.ui.common.adapter.BaseViewHolder
import com.example.marcin.meetfriends.utils.DateTimeFormatters
import kotlinx.android.synthetic.main.item_date_chart_row.view.*
import org.joda.time.DateTime

/**
 * Created by marci on 2018-01-02.
 */
class DateQuestionnaireViewHolder(itemView: View) : BaseViewHolder(itemView) {

  fun bind(dateRow: DateRow) {
    itemView.dateVote.text = DateTimeFormatters.formatToShortTimeDate(DateTime(dateRow.timestamp.toLong()))
    val votersList = dateRow.voters
    val votersAdapter = VotersAdapter(votersList)
    itemView.votersRecyclerView.layoutManager = GridLayoutManager(itemView.context, 4)
    itemView.votersRecyclerView.adapter = votersAdapter
  }

  companion object {
    fun create(parent: ViewGroup): DateQuestionnaireViewHolder {
      return DateQuestionnaireViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_date_chart_row, parent, false))
    }
  }
}