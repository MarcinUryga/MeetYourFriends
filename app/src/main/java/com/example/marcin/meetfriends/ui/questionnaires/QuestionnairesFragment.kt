package com.example.marcin.meetfriends.ui.questionnaires

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.EventDetailActivity
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.FragmentsItems
import com.example.marcin.meetfriends.ui.questionnaires.adapter.QuestionnaireAdapter
import com.google.firebase.database.DataSnapshot
import dagger.android.support.AndroidSupportInjection
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import kotlinx.android.synthetic.main.fragment_events_rooms.*

/**
 * Created by marci on 2017-12-12.
 */
class QuestionnairesFragment : BaseFragment<QuestionairesContract.Presenter>(), QuestionairesContract.View {

  private val postAdapter = QuestionnaireAdapter()

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onResume() {
    super.onResume()
    eventRoomsRecyclerView.layoutManager = LinearLayoutManager(context)
    eventRoomsRecyclerView.adapter = postAdapter
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_events_rooms, container, false)
  }

  override fun manageEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    emptyEventItemsLayout.visibility = View.INVISIBLE
    postAdapter.manageChildItem(dataSnapshot)
    presenter.handleChosenEventRoom(postAdapter.getClickEvent())
  }

  override fun getEventItemsSizeFromAdapter() = postAdapter.itemCount

  override fun startEventQuestionnaireFragment(eventBasicInfoParams: EventBasicInfoParams) {
    startActivity(EventDetailActivity.newIntent(context, eventBasicInfoParams, FragmentsItems.QUESTIONNAIRES))
  }

  override fun showLoading() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun showEmptyQuestionnairesToFillScreen() {
    emptyEventItemsLayout.visibility = View.VISIBLE
    noItemsImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_chart_green_icon))
    noItemsTextView.text = getString(R.string.you_have_any_questionnaires_to_fill)
  }

  override fun hideEmptyQuestionnairesToFillScreen() {
    emptyEventItemsLayout.visibility = View.INVISIBLE
  }
}