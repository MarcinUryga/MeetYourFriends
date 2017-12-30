package com.example.marcin.meetfriends.ui.create_event

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Place
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.choose_event_icon.ChooseEventIconDialogFragment
import com.example.marcin.meetfriends.ui.choose_event_icon.OnIconSelectedListener
import com.example.marcin.meetfriends.ui.choose_event_icon.viewmodel.EventIconEnum
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.places_adapter.PlacesAdapter
import com.example.marcin.meetfriends.ui.event_detail.EventDetailActivity
import com.example.marcin.meetfriends.ui.search_venues.SearchVenuesActivity
import com.example.marcin.meetfriends.utils.CircleTransform
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_create_event.*

/**
 * Created by marci on 2017-11-27.
 */
class CreateEventActivity : BaseActivity<CreateEventContract.Presenter>(), CreateEventContract.View, OnIconSelectedListener {

  var placesAdapter = PlacesAdapter(false)

  override fun onIconSelected(icon: EventIconEnum) {
    eventIconButton.tag = icon.resourceId
    Picasso.with(baseContext).load(icon.resourceId).transform(CircleTransform()).into(eventIconButton)
    Picasso.with(baseContext).load(icon.resourceId).into(eventImage)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_event)
    supportActionBar?.title = " ${getString(R.string.create_new_event)}"
    onIconSelected(EventIconEnum.BEER)
    createEventButton.setOnClickListener {
      presenter.tryToCreateEvent()
    }
    backgroundImageButton.setOnClickListener {
      Toast.makeText(baseContext, "select backroud clicked", Toast.LENGTH_SHORT).show()
    }
    eventIconButton.setOnClickListener {
      presenter.clickedEventIconButton()
    }
    findVenuesButton.setOnClickListener {
      presenter.searchVenues()
    }
    venuesRecyclerView.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
    setupAdapter()
  }

  override fun validateEventName(): Boolean {
    eventNameInputLayout.error = null
    if (eventNameEditText.text.isEmpty()) {
      eventNameInputLayout.error = getString(R.string.empty_event_name)
      return false
    }
    return true
  }

  override fun validateEventDescription(): Boolean {
    eventDescriptionInputLayout.error = null
    if (eventDescriptionEditText.text.isEmpty()) {
      eventDescriptionInputLayout.error = getString(R.string.empty_event_description)
      return false
    }
    return true
  }

  fun setupAdapter() {
    venuesRecyclerView.adapter = placesAdapter
    presenter.handleClickedActionButton(placesAdapter.getClickedActionButtonEvent())
  }

  override fun addPlaceToAdapter(venue: Place) {
    placesAdapter.addPlace(venue)
  }

  override fun removePlaceFromAdapter(place: Place) {
    placesAdapter.removePlace(place)
    Toast.makeText(baseContext, "Removed: ${place.name}", Toast.LENGTH_SHORT).show()
  }

  override fun clearAdapter() {
    placesAdapter.clearPlaceList()
  }

  override fun getEventName() = eventNameEditText.text.toString()

  override fun getEventDescription() = eventDescriptionEditText.text.toString()

  override fun getEventIconId() = eventIconButton.tag.toString()

  override fun openEventDetailsActivity(eventBasicInfoParams: EventBasicInfoParams) {
    startActivity(EventDetailActivity.newIntent(baseContext, eventBasicInfoParams))
    finish()
  }

  override fun startSelectEventIconDialog() {
    val createEventDialogFragment = ChooseEventIconDialogFragment()
    createEventDialogFragment.show(supportFragmentManager, ChooseEventIconDialogFragment::class.java.toString())
  }

  override fun startSearchVenuesActivity() {
    startActivity(SearchVenuesActivity.newIntent(this))
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, CreateEventActivity::class.java)
    }
  }
}