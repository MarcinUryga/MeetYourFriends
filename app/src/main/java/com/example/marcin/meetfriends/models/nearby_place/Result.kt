package com.example.marcin.meetfriends.models.nearby_place

import com.example.marci.googlemaps.pojo.Geometry
import com.example.marci.googlemaps.pojo.OpeningHours
import com.example.marci.googlemaps.pojo.Photo
import com.google.gson.annotations.SerializedName

/**
 * Created by marci on 2017-12-29.
 */
data class Result(
    @SerializedName("address_components")
    val addressComponents: List<AddressComponent>,
    @SerializedName("adr_address")
    val adrAddress: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    @SerializedName("formatted_phone_number")
    val formattedPhoneNumber: String,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours? = null,
    val photos: List<Photo>,
    val geometry: Geometry,
    val icon: String,
    val id: String,
    @SerializedName("international_phone_number")
    val internationalPhoneNumber: String,
    val name: String,
    @SerializedName("place_id")
    val placeId: String,
    val rating: Double,
    val reference: String,
    val reviews: List<Review>,
    val scope: String,
    val types: List<String>,
    val url: String,
    @SerializedName("utc_offset")
    val utcOffset: Int,
    val vicinity: String,
    val website: String
)