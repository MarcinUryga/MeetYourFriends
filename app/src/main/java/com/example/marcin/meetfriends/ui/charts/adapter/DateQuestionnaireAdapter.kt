package com.example.marcin.meetfriends.ui.charts.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.ChartRow

/**
 * Created by marci on 2018-01-02.
 */
class DateQuestionnaireAdapter : RecyclerView.Adapter<DateQuestionnaireViewHolder>() {

  private var chartRowsList = listOf<ChartRow>()

  fun initChartRowsList(chartRowsList: List<ChartRow>) {
    this.chartRowsList = chartRowsList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DateQuestionnaireViewHolder.create(parent)

  override fun onBindViewHolder(holder: DateQuestionnaireViewHolder, position: Int) = holder.bind(chartRowsList[position])

  override fun getItemCount() = chartRowsList.size
}