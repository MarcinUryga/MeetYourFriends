package com.example.marcin.meetfriends.ui.search_venues

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.search_venues.adapter.VenuesAdapter
import com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search_venues.*

/**
 * Created by marci on 2017-12-24.
 */
class SearchVenuesActivity : BaseActivity<SearchVenuesContract.Presenter>(), SearchVenuesContract.View {

  private var venuesAdapter = VenuesAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_venues)
    searchVenuesButton.setOnClickListener {
      venuesAdapter.clearVenuesList()
      presenter.getNearbyPlaces(placeTypeEditText.text.toString())
    }
    venuesRecyclerView.layoutManager = LinearLayoutManager(baseContext)
    venuesRecyclerView.adapter = venuesAdapter
  }

  override fun showVenues(venues: MutableList<Place>) {
    emptyVenuesListLayout.visibility = View.INVISIBLE
    venuesAdapter.createVenuesList(venues)
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
        .setMessage("Please Turn ON your GPS Connection")
        .setCancelable(false)
        .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
        .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        .show()
  }

  companion object {
    fun newIntent(context: Context): Intent {
      return Intent(context, SearchVenuesActivity::class.java)
    }
  }
}