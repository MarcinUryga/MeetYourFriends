package com.example.marcin.meetfriends.ui.place_details

import com.example.marcin.meetfriends.maps_api.GoogleMapsApi
import com.example.marcin.meetfriends.models.nearby_place.PlaceDetails
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by marci on 2017-12-29.
 */
class GetPlaceDetailsUseCase @Inject constructor(
    private val googleMapsApi: GoogleMapsApi
) {

  fun get(placeId: String): Single<PlaceDetails> {
    return googleMapsApi.getPlaceDetails(placeId)
  }
}