package com.example.marcin.meetfriends.ui.common.use_cases

import com.example.marci.googlemaps.pojo.NearbyPlaces
import com.example.marcin.meetfriends.maps_api.GoogleMapsApi
import com.example.marcin.meetfriends.models.nearby_place.DistanceMatrix
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by marci on 2017-12-24.
 */
class GetNearbyPlacesUseCase @Inject constructor(
    private val googleMapsApi: GoogleMapsApi
) {

  fun getPlaces(type: String, keyword: String, location: String): Single<NearbyPlaces> {
    return googleMapsApi.getNearbyPlaces(type, keyword, location)
  }

  fun getDistanceMatrix(origins: String, destinations: String): Single<DistanceMatrix> {
    return googleMapsApi.getDistanceMatrix(origins, destinations)
  }
}