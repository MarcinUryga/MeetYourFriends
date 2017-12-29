package com.example.marcin.meetfriends.ui.search_venues

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.marci.googlemaps.pojo.Location
import javax.inject.Inject

/**
 * Created by marci on 2017-12-29.
 */
class GetDeviceLocationUseCase @Inject constructor(
    private val locationManager: LocationManager,
    private val context: Context
) {

  private var location = Location(0.0, 0.0)
  private val REQUEST_LOCATION = 1

  fun get(activity: Activity): Location {
    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
    } else {
      val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
      val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
      val location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
      when {
        location != null -> {
          this.location = Location(location.latitude, location.longitude)
        }
        location1 != null -> {
          this.location = Location(location1.latitude, location1.longitude)
        }
        location2 != null -> {
          this.location = Location(location2.latitude, location2.longitude)
        }
        else -> Toast.makeText(context, "Unble to Trace your location", Toast.LENGTH_SHORT).show()
      }
    }
    return location
  }
}