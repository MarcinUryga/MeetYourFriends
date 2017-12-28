package com.example.marcin.meetfriends.ui.search_venues

import com.example.marci.googlemaps.pojo.NearbyPlaces
import com.example.marcin.meetfriends.maps_api.GoogleMapsApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by marci on 2017-12-24.
 */
class GetNearbyPlacesUseCase @Inject constructor(
    private val googleMapsApi: GoogleMapsApi
) {

  fun get(type: String, location: String, radius: Int): Single<NearbyPlaces> {
    return googleMapsApi.getNearbyPlaces(type, location/*, radius*/)
  }
}