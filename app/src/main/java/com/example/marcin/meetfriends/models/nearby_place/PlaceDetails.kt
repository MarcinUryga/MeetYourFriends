package com.example.marcin.meetfriends.models.nearby_place

import com.google.gson.annotations.SerializedName

/**
 * Created by marci on 2017-12-29.
 */
data class PlaceDetails(
    @SerializedName("html_attributions")
    val htmlAttributions: List<Any>,
    val result: Result,
    val status: String
)