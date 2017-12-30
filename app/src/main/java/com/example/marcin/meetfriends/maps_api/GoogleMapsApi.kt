package com.example.marcin.meetfriends.maps_api

import com.example.marci.googlemaps.pojo.NearbyPlaces
import com.example.marcin.meetfriends.models.nearby_place.DistanceMatrix
import com.example.marcin.meetfriends.models.nearby_place.PlaceDetails
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

  @GET("api/place/details/json?&key=$GOOGLE_MAPS_API_KEY")
  fun getPlaceDetails(@Query("placeid") placeId: String): Single<PlaceDetails>

  companion object {
    const val GOOGLE_MAPS_API_KEY = "AIzaSyDSduchQQv2t1Zsp1VTwFtM4ec-IjqfdjA"
  }
}