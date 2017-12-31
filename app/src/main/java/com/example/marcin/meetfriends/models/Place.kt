package com.example.marcin.meetfriends.models

import com.example.marci.googlemaps.pojo.Location
import com.example.marci.googlemaps.pojo.Photo
import com.example.marcin.meetfriends.maps_api.GoogleMapsApi.Companion.GOOGLE_MAPS_API_KEY
import com.example.marcin.meetfriends.models.nearby_place.Distance
import com.example.marcin.meetfriends.models.nearby_place.Duration

/**
 * Created by marci on 2017-12-28.
 */
data class Place(
    val id: String,
    val name: String,
    val rating: Double,
    val location: Location,
    val vicinity: String?,
    val distance: Distance? = null,
    val duration: Duration? = null,
    var photos: List<Photo>? = null,
    val placeIcon: String? = null,
    var isAdded: Boolean = false
) {

  fun getPhotosUrl(): List<String> {
    val photosUrl = mutableListOf<String>()
    photos?.forEach {
      photosUrl.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=${it.width}&photoreference=${it.photoReference}&key=$GOOGLE_MAPS_API_KEY")
    }
    return photosUrl
  }
}
