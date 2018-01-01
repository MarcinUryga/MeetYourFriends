package com.example.marcin.meetfriends.ui.place_details.viewmodel

import android.content.Context
import com.example.marci.googlemaps.pojo.Location
import com.example.marci.googlemaps.pojo.Photo
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.maps_api.GoogleMapsApi
import com.example.marcin.meetfriends.models.nearby_place.Review

/**
 * Created by marci on 2017-12-29.
 */
data class PlaceDetails(
    val id: String,
    val photos: List<Photo>? = null,
    val location: Location,
    val name: String,
    val address: String,
    val placeIcon: String,
    val phoneNumber: String? = null,
    val websiteUrl: String? = null,
    val rating: Double,
    val weekdayOpeningHours: List<String>? = emptyList(),
    val reviews: List<Review>? = emptyList()
) {

  fun getPhotosUrl(): List<String> {
    val photosUrl = mutableListOf<String>()
    photos?.forEach {
      photosUrl.add("https://maps.googleapis.com/maps/api/place/photo?maxwidth=${it.width}&photoreference=${it.photoReference}&key=${GoogleMapsApi.GOOGLE_MAPS_API_KEY}")
    }
    return photosUrl
  }

  fun getWeekDayOpeningHoursString(context: Context): String {
    val string = StringBuilder()
    if (weekdayOpeningHours == null) {
      return context.getString(R.string.opening_hours_is_not_precized)
    }
    weekdayOpeningHours.forEach {
      string.append("$it\n")
    }
    return string.toString()
  }
}