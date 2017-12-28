package com.example.marcin.meetfriends.ui.search_venues

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
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

  private val REQUEST_LOCATION = 1
  private lateinit var locationManager: LocationManager
  private val venuesAdapter = VenuesAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_venues)
    locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      buildAlertMessageNoGps()
    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      getLocation()
    }
    venuesRecyclerView.layoutManager = LinearLayoutManager(baseContext)
    venuesRecyclerView.adapter = venuesAdapter
  }

  override fun showVenues(venues: List<Place>) {
    venuesAdapter.createVenuesList(venues)
  }

  override fun showProgressBar() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgressBar() {
    progressBar.visibility = View.INVISIBLE
  }

  private fun getLocation() {
    if (ActivityCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
    } else {
      val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
      val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
      val location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
      when {
        location != null -> {
          val lat = location!!.getLatitude()
          val lng = location!!.getLongitude()
        }
        location1 != null -> {
          val lat = location1!!.getLatitude()
          val lng = location1!!.getLongitude()

        }
        location2 != null -> {
          val lat = location2!!.getLatitude()
          val lng = location2!!.getLongitude()
        }
        else -> Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun buildAlertMessageNoGps() {
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