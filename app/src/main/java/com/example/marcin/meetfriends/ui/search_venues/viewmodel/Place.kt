package com.example.marcin.meetfriends.ui.search_venues.viewmodel

import android.content.Context
import com.example.marci.googlemaps.pojo.Location
import com.example.marci.googlemaps.pojo.Photo
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.maps_api.GoogleMapsApi.Companion.GOOGLE_MAPS_API_KEY
import com.example.marcin.meetfriends.models.nearby_place.Distance
import com.example.marcin.meetfriends.models.nearby_place.Duration
import java.text.DecimalFormat

/**
 * Created by marci on 2017-12-28.
 */
data class Place(
    val id: String,
    val name: String,
    val rating: Double,
    val location: Location,
    val vicinity: String,
    val distance: Distance,
    val duration: Duration,
    var photos: List<Photo>? = null,
    var isAdded: Boolean = false
) {

  fun transformDistance(context: Context): String {
    val distanceInMeters = distance.value
    if (distanceInMeters >= 1000) {
      return context.getString(R.string.kilometers, DecimalFormat(".##").format(distanceInMeters.toDouble() / 1000)
      )
    }
    return context.getString(R.string.meters, distanceInMeters.toString())
  }

  fun getPhotosUrl(): List<String> {
    val photosUrl = mutableListOf<String>()
    val width = 400
    val ref = "CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU"
    photos?.forEach {
      photosUrl.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=${it.width}&photoreference=${it.photoReference}&key=$GOOGLE_MAPS_API_KEY")
    }
    return photosUrl
  }
}
