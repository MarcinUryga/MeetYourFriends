package com.example.marcin.meetfriends.ui.confirmed_event_detail

import android.app.Activity
import android.content.res.Resources
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.params.PlaceIdParams

/**
 * Created by marci on 2018-01-03.
 */
interface ConfirmedEventContract {

  interface View : MvpView {

    fun showProgressBar()

    fun hideProgressBar()

    fun showVenueProgressBar()

    fun hideVenueProgressBar()

    fun showParticipantsProgressBar()

    fun hideParticipantsProgressBar()

    fun showEventData(event: Event)

    fun showParticipantsRecyclerView(participantsList: List<User>)

    fun showEventVenueCardView(venue: FirebasePlace)

    fun buildAlertMessageNoGps()

    fun getActivity(): Activity

    fun startNavigateToEventVenue(currentLocation: String, eventVenueLatLang: String)

    fun showOrganizerData(organizer: User) {}

    fun openPlacDetailsActivity(placeIdParams: PlaceIdParams)

    fun startEventChatActivity(eventBasicInfoParams: EventBasicInfoParams)

    fun openDeleteButtonDialog(message: String)

    fun startMainActivity()
  }

  interface Presenter : MvpPresenter {

    fun clickedOpenGoogleMapsButton()

    fun clickedVenueCardView()

    fun navigateToEventChat()

    fun onDeleteClicked(resources: Resources)

    fun deleteEvent()
  }
}