package com.example.marcin.meetfriends.maps_api

import com.example.marci.googlemaps.pojo.NearbyPlaces
import com.example.marcin.meetfriends.models.nearby_place.DistanceMatrix
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by marci on 2017-12-24.
 */
interface GoogleMapsApi {

  @GET("api/place/nearbysearch/json?sensor=true&rankby=distance&key=$GOOGLE_MAPS_API_KEY")
  fun getNearbyPlaces(@Query("type") type: String, @Query("keyword") keyword: String, @Query("location") location: String): Single<NearbyPlaces>

  @GET("api/distancematrix/json?units=imperial&language=pl&key=$GOOGLE_MAPS_API_KEY")
  fun getDistanceMatrix(@Query("origins") origins: String, @Query("destinations") destinations: String): Single<DistanceMatrix>

  companion object {
    const val GOOGLE_MAPS_API_KEY = "AIzaSyBwRrDYl7nHOy_QK9ERNSsXDJCDaVaPAk0"
  }
}