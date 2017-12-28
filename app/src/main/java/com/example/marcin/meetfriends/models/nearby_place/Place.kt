package com.example.marci.googlemaps.pojo

import com.google.gson.annotations.SerializedName

/**
 * Created by marci on 2017-12-22.
 */
data class Place(
    val geometry: Geometry,
    val icon: String,
    val id: String,
    val name: String,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours,
    val photos: List<Photo>,
    @SerializedName("place_id")
    val placeId: String,
    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,
    val vicinity: String
)