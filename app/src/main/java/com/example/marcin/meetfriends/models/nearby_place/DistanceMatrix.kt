package com.example.marcin.meetfriends.models.nearby_place

import com.google.gson.annotations.SerializedName

/**
 * Created by marci on 2017-12-28.
 */
data class DistanceMatrix(
    @SerializedName("destination_addresses")
    val destinationAddresses: List<String>,
    @SerializedName("origin_addresses")
    val originAddresses: List<String>,
    val rows: List<Row>,
    val status: String
)