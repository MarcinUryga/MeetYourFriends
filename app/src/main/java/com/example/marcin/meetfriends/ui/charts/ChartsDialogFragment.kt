package com.example.marcin.meetfriends.ui.charts

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.ChartRow
import com.example.marcin.meetfriends.mvp.BaseFragmentDialog
import com.example.marcin.meetfriends.ui.charts.adapter.DateQuestionnaireAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_charts.*

/**
 * Created by marci on 2018-01-02.
 */
class ChartsDialogFragment : BaseFragmentDialog<ChartsContract.Presenter>(), ChartsContract.View {

  private val dateQuestionnaireAdapter = DateQuestionnaireAdapter()

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
  }

  override fun showQuestionnairesResult(chartRowsList: List<ChartRow>) {
    dateQuestionnaireAdapter.initChartRowsList(chartRowsList)
  }
}