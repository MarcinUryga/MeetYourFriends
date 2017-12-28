package com.example.marci.googlemaps.pojo

import com.google.gson.annotations.SerializedName


/**
 * Created by marci on 2017-12-22.
 */
data class NearbyPlaces(
    @SerializedName("html_attributions")
    val htmlAttributions: List<Any>,
    @SerializedName("next_page_token")
    val nextPageToken: String,
    @SerializedName("results")
    val places: List<Place>,
    val status: String
)