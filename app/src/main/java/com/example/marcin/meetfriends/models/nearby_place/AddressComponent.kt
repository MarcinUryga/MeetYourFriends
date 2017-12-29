package com.example.marcin.meetfriends.models.nearby_place

import com.google.gson.annotations.SerializedName

/**
 * Created by marci on 2017-12-29.
 */
data class AddressComponent(
    @SerializedName("long_name ")
    val longName: String,
    @SerializedName("short_name")
    val shortName: String,
    val types: List<String>
)