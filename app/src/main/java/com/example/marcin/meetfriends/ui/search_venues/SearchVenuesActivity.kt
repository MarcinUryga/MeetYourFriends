package com.example.marcin.meetfriends.ui.search_venues

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Place
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.common.PlaceIdParams
import com.example.marcin.meetfriends.ui.common.places_adapter.PlacesAdapter
import com.example.marcin.meetfriends.ui.place_details.PlaceDetailsActivity
import com.example.marcin.meetfriends.utils.KeyboardUtils
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search_venues.*

/**
 * Created by marci on 2017-12-24.
 */
class SearchVenuesActivity : BaseActivity<SearchVenuesContract.Presenter>(), SearchVenuesContract.View {

  private val placesAdapter = PlacesAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_venues)
    searchVenuesButton.setOnClickListener {
      hideKeyboard()
      presenter.getNearbyPlaces(placeTypeEditText.text.toString())
    }
    venuesRecyclerView.layoutManager = LinearLayoutManager(this)
    venuesRecyclerView.adapter = placesAdapter
    setUpAdapter()
  }

  override fun addPlaceToAdapter(place: Place) {
    emptyVenuesListLayout.visibility = View.INVISIBLE
    placesAdapter.addPlace(place)
  }

  fun setUpAdapter() {
    presenter.handleChosenPlace(placesAdapter.getClickEvent())
    presenter.handleClickedActionButton(placesAdapter.getClickedActionButtonEvent())
  }

  override fun showProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun showEmptyVenuesList(type: String) {
    noVenuesTextView.text = getString(R.string.no_results_for_type_and_kayword, type)
    emptyVenuesListLayout.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun buildAlertMessageNoGps() {
    AlertDialog.Builder(this)
        .setMessage(getString(R.string.please_turn_on_gps))
        .setCancelable(false)
        .setPositiveButton("Yes", { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
        .setNegativeButton("No", { dialog, _ -> dialog.cancel() })
        .show()
  }

  override fun startPlaceDetailsActivity(params: PlaceIdParams) {
    startActivity(PlaceDetailsActivity.newIntent(baseContext, params))
  }

  override fun showToast(text: String) {
    Toast.makeText(baseContext, text, Toast.LENGTH_SHORT).show()
  }

  private fun hideKeyboard() {
    KeyboardUtils.hide(this.currentFocus)
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, SearchVenuesActivity::class.java)
    }
  }
}