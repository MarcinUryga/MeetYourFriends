package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created by marci on 2017-12-05.
 */
class EventQuestionnaireFragment : BaseFragment<EventQuestionnaireContract.Presenter>(), EventQuestionnaireContract.View {

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_event_questionnaire, container, false)
  }
}