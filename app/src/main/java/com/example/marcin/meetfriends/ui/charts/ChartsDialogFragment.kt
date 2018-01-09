package com.example.marcin.meetfriends.ui.charts

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.DateRow
import com.example.marcin.meetfriends.models.VenueRow
import com.example.marcin.meetfriends.mvp.BaseFragmentDialog
import com.example.marcin.meetfriends.ui.charts.adapter.chart.ChartAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_charts.*

/**
 * Created by marci on 2018-01-02.
 */
class ChartsDialogFragment : BaseFragmentDialog<ChartsContract.Presenter>(), ChartsContract.View {

  private val dateQuestionnaireAdapter = ChartAdapter()
  private val venueQuestionnaireAdapter = ChartAdapter()

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
    return inflater.inflate(R.layout.fragment_charts, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dateQuestionnaireRecyclerView.layoutManager = LinearLayoutManager(context)
    dateQuestionnaireRecyclerView.adapter = dateQuestionnaireAdapter
    venueQuestionnaireRecyclerView.layoutManager = LinearLayoutManager(context)
    venueQuestionnaireRecyclerView.adapter = venueQuestionnaireAdapter
  }

  override fun showDateQuestionnaireProgressBar() {
    dateChartProgressBar.visibility = View.VISIBLE
  }

  override fun hideDateQuestionnaireProgressBar() {
    dateChartProgressBar.visibility = View.INVISIBLE
  }

  override fun showVenueQuestionnaireProgressBar() {
    venuesChartProgressBar.visibility = View.VISIBLE
  }

  override fun hideVenueQuestionnaireProgressBar() {
    venuesChartProgressBar.visibility = View.INVISIBLE
  }

  override fun showNoDateVotes() {
    dateChartProgressBar.visibility = View.INVISIBLE
    noDateVotesImageView.visibility = View.VISIBLE
  }

  override fun showNoVenueVotes() {
    venuesChartProgressBar.visibility = View.INVISIBLE
    noVenueVotesImageView.visibility = View.VISIBLE
  }

  override fun showDateQuestionnairesResult(dateRowsList: List<DateRow>) {
    dateQuestionnaireAdapter.initChartRowsList(dateRowsList)
  }

  override fun showVenueQuestionnairesResult(venueRowsList: List<VenueRow>) {
    venueQuestionnaireAdapter.initChartRowsList(venueRowsList)
  }
}