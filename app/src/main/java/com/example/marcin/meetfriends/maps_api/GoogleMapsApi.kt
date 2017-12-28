package com.example.marcin.meetfriends.maps_api

import com.example.marci.googlemaps.pojo.NearbyPlaces
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by marci on 2017-12-24.
 */
interface GoogleMapsApi {

  @GET("api/place/nearbysearch/json?sensor=true&rankby=distance&key=AIzaSyBwRrDYl7nHOy_QK9ERNSsXDJCDaVaPAk0")
  fun getNearbyPlaces(@Query("type") type: String, @Query("location") location: String/*, @Query("radius") radius: Int*/): Single<NearbyPlaces>
}