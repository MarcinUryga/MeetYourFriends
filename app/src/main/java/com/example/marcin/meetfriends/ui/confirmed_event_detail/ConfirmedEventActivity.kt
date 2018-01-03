package com.example.marcin.meetfriends.ui.confirmed_event_detail

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.extensions.transformDistance
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.chat.ChatActivity
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.common.PlaceIdParams
import com.example.marcin.meetfriends.ui.confirmed_event_detail.adapter.ParticipantsAdapter
import com.example.marcin.meetfriends.ui.main.MainActivity
import com.example.marcin.meetfriends.ui.place_details.PlaceDetailsActivity
import com.example.marcin.meetfriends.utils.CircleTransform
import com.example.marcin.meetfriends.utils.DateTimeFormatters
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_confirmed_event.*
import kotlinx.android.synthetic.main.content_event_confirmed.*
import kotlinx.android.synthetic.main.content_event_description.*
import kotlinx.android.synthetic.main.item_event_description.*
import kotlinx.android.synthetic.main.item_event_participants.*
import kotlinx.android.synthetic.main.item_venue.*
import org.joda.time.DateTime

/**
 * Created by marci on 2018-01-03.
 */
class ConfirmedEventActivity : BaseActivity<ConfirmedEventContract.Presenter>(), ConfirmedEventContract.View {

  private var participantsAdapter = ParticipantsAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_confirmed_event)
    participantsRecyclerView.layoutManager = GridLayoutManager(baseContext, 3)
    participantsRecyclerView.adapter = participantsAdapter
    openGoogleMapsButton.setOnClickListener {
      presenter.clickedOpenGoogleMapsButton()
    }
    venueCardView.setOnClickListener {
      presenter.clickedVenueCardView()
    }
  }

  override fun showProgressBar() {
    confirmedEventProgressBar.visibility = View.VISIBLE
  }

  override fun getActivity() = this

  override fun hideProgressBar() {
    confirmedEventProgressBar.visibility = View.INVISIBLE
  }

  override fun showEventData(event: Event) {
    eventDataLayout.visibility = View.VISIBLE
    eventDescriptionLayout.visibility = View.VISIBLE
    eventDescriptionTextView.text = event.description
    Picasso.with(baseContext).load(event.iconId).into(eventImage)
    eventDateTextView.text = DateTimeFormatters.formatToShortTimeDate(DateTime(event.date?.toLong()))
    openChatButton.setOnClickListener {
      presenter.navigateToEventChat()
    }
    deleteEventButton.setOnClickListener {
      presenter.onDeleteClicked(resources)
    }
  }

  override fun showOrganizerData(organizer: User) {
    Picasso.with(baseContext).load(organizer.photoUrl).placeholder(R.drawable.placeholder).transform(CircleTransform()).into(organizerPhoto)
    organizerDisplayNameTextView.text = organizer.displayName
  }

  override fun showParticipantsRecyclerView(participantsList: List<User>) {
    eventParticipantsLayout.visibility = View.VISIBLE
    participantsAdapter.initParticipantsList(participantsList)
  }

  override fun showVenueProgressBar() {
    venueProgressBar.visibility = View.VISIBLE
  }

  override fun hideVenueProgressBar() {
    venueProgressBar.visibility = View.INVISIBLE
  }

  override fun showParticipantsProgressBar() {
    eventParticipantsProgressBar.visibility = View.VISIBLE
  }

  override fun hideParticipantsProgressBar() {
    eventParticipantsProgressBar.visibility = View.INVISIBLE
  }

  override fun showEventVenueCardView(venue: FirebasePlace) {
    venueCardView.visibility = View.VISIBLE
    openGoogleMapsButton.visibility = View.VISIBLE
    if (venue.photos.isNotEmpty()) {
      Picasso.with(baseContext).load(venue.photos.first()).fit().into(venueImage)
    } else if (venue.placeIcon != null) {
      Picasso.with(baseContext).load(venue.photos.first()).fit().into(venueImage)
    }
    venueNameTextView.text = venue.name
    venueVicinityTextView.text = venue.vicinity
    distanceTextView.text = venue.distance?.transformDistance(baseContext)
    ratingTextView.text = venue.rating.toString()
  }

  override fun buildAlertMessageNoGps() {
    AlertDialog.Builder(baseContext)
        .setMessage(getString(R.string.please_turn_on_gps))
        .setCancelable(false)
        .setPositiveButton("Yes", { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
        .setNegativeButton("No", { dialog, _ -> dialog.cancel() })
        .show()
  }

  override fun startNavigateToEventVenue(currentLocation: String, eventVenueLatLang: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(GOOGLE_MAPS_URL)
        .buildUpon()
        .appendQueryParameter("destination", "$eventVenueLatLang")
        .build()
    startActivity(intent)
  }

  override fun startEventChatActivity(eventBasicInfoParams: EventBasicInfoParams) {
    startActivity(ChatActivity.newIntent(baseContext, eventBasicInfoParams))
  }

  override fun openPlacDetailsActivity(placeIdParams: PlaceIdParams) {
    startActivity(PlaceDetailsActivity.newIntent(baseContext, placeIdParams))
  }

  override fun openDeleteButtonDialog(message: String) {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.delete_event))
        .setMessage(message)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton(android.R.string.yes) { _, _ ->
          presenter.deleteEvent()
        }
        .setNegativeButton(android.R.string.no, null)
        .show()
  }

  override fun startMainActivity() {
    startActivity(MainActivity.newIntent(baseContext))
  }

  companion object {
    private val GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/?api=1"

    fun newIntent(context: Context, eventIdParams: EventIdParams): Intent {
      val intent = Intent(context, ConfirmedEventActivity::class.java)
      intent.putExtras(eventIdParams.data)
      return intent
    }
  }
}