package com.example.marcin.meetfriends.ui.place_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import com.example.marci.googlemaps.pojo.Location
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.common.PlaceIdParams
import com.example.marcin.meetfriends.ui.place_details.adapters.review_adapter.ReviewAdapter
import com.example.marcin.meetfriends.ui.place_details.viewmodel.PlaceDetails
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_place_details.*
import kotlinx.android.synthetic.main.content_place_details.*

/**
 * Created by marci on 2017-12-29.
 */
class PlaceDetailsActivity : BaseActivity<PlaceDetailsContract.Presenter>(), PlaceDetailsContract.View {

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_place_details)
    reviewRecyclerView.layoutManager = LinearLayoutManager(this)
    openGoogleMapsButton.setOnClickListener {
      presenter.clickedOpenGoogleMapsButton()
    }
  }

  override fun showProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun showPlaceDetails(placeDetails: PlaceDetails) {
    placeDetailsLayout.visibility = View.VISIBLE
    addressTextView.text = placeDetails.address
    phoneNumberTextView.text = placeDetails.phoneNumber
    websiteUrlTextView.text = placeDetails.websiteUrl
    Linkify.addLinks(websiteUrlTextView, Linkify.WEB_URLS)
    websiteUrlTextView.movementMethod = LinkMovementMethod.getInstance()
    ratingTextView.text = placeDetails.rating.toString()
    openingHoursTextView.text = placeDetails.getWeekDayOpeningHoursString(baseContext)
    Picasso.with(baseContext).load(placeDetails.getPhotosUrl().first()).placeholder(R.drawable.placeholder).into(venueImage)
    toolbar.title = placeDetails.name
    setSupportActionBar(toolbar)
    reviewRecyclerView.adapter = ReviewAdapter(placeDetails.reviews)
  }

  override fun startGoogleMaps(placeLocation: Location) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(GOOGLE_MAPS_URL)
        .buildUpon()
        .appendQueryParameter("destination", "${placeLocation.lat},${placeLocation.lng}")
        .build()
    startActivity(intent)
  }

  companion object {

    private val GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/?api=1"

    fun newIntent(context: Context, placeIdParams: PlaceIdParams): Intent {
      val intent = Intent(context, PlaceDetailsActivity::class.java)
      intent.putExtras(placeIdParams.data)
      return intent
    }

  }
}