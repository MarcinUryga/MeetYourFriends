package com.example.marcin.meetfriends.ui.planned_event_detail.event_questionnaire

import android.app.Activity
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.common.PlaceIdParams
import io.reactivex.Observable
import org.joda.time.DateTime

/**
 * Created by marci on 2017-12-05.
 */
interface EventQuestionnaireContract {

  interface View : MvpView {

    fun showChosenDateSnackBar(selectedDate: DateTime, userId: String)

    fun initializeVenuesAdapter(venues: List<FirebasePlace>)

    fun setUpAdapterListeners()

    fun showDateSectionProgressBar()

    fun hideDateSectionProgressBar()

    fun showDateChooserLayout()

    fun showVenuesSectionProgressBar()

    fun hideVenuesSectionProgressBar()

    fun buildAlertMessageNoGps()

    fun startPlaceDetailsActivity(placeIdParams: PlaceIdParams)

    fun showChosenVenueSnackBar(venue: FirebasePlace, userId: String)

    fun getActivity(): Activity

    fun showFilledDateQuestionnaire(date: DateTime)

    fun showDateChoserLayout()

    fun showFilledVenueQuestionnaire(venue: FirebasePlace)

    fun showVenueChoserLayout()

    fun startChartsDialogFragment(eventIdParams: EventIdParams)
  }

  interface Presenter : MvpPresenter {

    fun sendDateVote(selectedDate: DateTime)

    fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String)

    fun handleChosenPlace(clickEvent: Observable<FirebasePlace>)

    fun handleClickedActionButton(clickedActionButtonEvent: Observable<FirebasePlace>)

    fun removeChosenVenueFromEvent(venueId: String, userId: String)

    fun clickedChangeDateButton()

    fun clickedChangeVenueButton()

    fun clickedChartsButton()

    fun onClickSelectedVenue()
  }
}