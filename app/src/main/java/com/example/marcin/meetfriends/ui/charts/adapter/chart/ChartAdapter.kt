package com.example.marcin.meetfriends.ui.charts.adapter.chart

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.ChartRow
import com.example.marcin.meetfriends.models.DateRow
import com.example.marcin.meetfriends.models.VenueRow
import com.example.marcin.meetfriends.ui.common.adapter.BaseViewHolder

/**
 * Created by marci on 2018-01-02.
 */
class ChartAdapter : RecyclerView.Adapter<BaseViewHolder>() {

  private var chartRowsList = listOf<ChartRow>()

  companion object {
    private val DATE_CHART = 0
    private val VENUE_CHART = 1
  }

  fun initChartRowsList(rowsList: List<ChartRow>) {
    chartRowsList = rowsList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    return when (viewType) {
      DATE_CHART -> DateQuestionnaireViewHolder.create(parent)
      VENUE_CHART -> VenueQuestionnaireViewHolder.create(parent)
      else -> BaseViewHolder.empty(parent)
    }
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    when (holder.itemViewType) {
      DATE_CHART -> (holder as? DateQuestionnaireViewHolder)?.bind(chartRowsList[position] as DateRow)
      VENUE_CHART -> (holder as? VenueQuestionnaireViewHolder)?.bind(chartRowsList[position] as VenueRow)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return if (chartRowsList[position] is DateRow) DATE_CHART else VENUE_CHART
  }

  override fun getItemCount() = chartRowsList.size
}