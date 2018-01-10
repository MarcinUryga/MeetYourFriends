package com.example.marcin.meetfriends.ui.planned_event_detail

import android.content.res.Resources
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.params.EventIdParams

/**
 * Created by marci on 2017-11-28.
 */
interface EventDetailContract {

  interface View : MvpView {

    fun setUpToolbarEventName(eventName: String)

    fun setEventImage(imageId: Int)

    fun setUpOrganizerData(organizer: User)

    fun startEventChatActivity(params: EventBasicInfoParams)

    fun switchToEventDescriptionFragment(arguments: EventBasicInfoParams)

    fun switchToEventQuestionnaireFragment(arguments: EventBasicInfoParams)

    fun openDeleteButtonDialog(message: String)

    fun startMainActivity()

    fun hideFinishVotingButton()

    fun startConfirmedEventActivity(eventIdParams: EventIdParams)

    fun showToast(message: String)

    fun showProgressDialog()

    fun hideProgressDialog()
  }

  interface Presenter : MvpPresenter {

    fun navigateToEventChat()

    fun navigateToEventDescription()

    fun navigateToEventQuestionnaire()

    fun onDeleteClicked(resources: Resources)

    fun deleteEvent()

    fun clickedFinishVotingButton()
  }
}