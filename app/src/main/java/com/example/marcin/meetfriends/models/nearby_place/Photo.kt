package com.example.marci.googlemaps.pojo

import com.google.gson.annotations.SerializedName


/**
 * Created by marci on 2017-12-22.
 */
data class Photo(
    var height: Int?,
    @SerializedName("html_attributions")
    var htmlAttributions: List<String>,
    @SerializedName("photo_reference")
    var photoReference: String?,
    var width: Int?
)