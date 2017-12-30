package com.example.marci.googlemaps.pojo

import com.example.marcin.meetfriends.models.nearby_place.Period
import com.google.gson.annotations.SerializedName


/**
 * Created by marci on 2017-12-22.
 */
data class OpeningHours(
    @SerializedName("open_now")
    var openNow: Boolean? = null,
    @SerializedName("weekday_text")
    val weekdayText: List<String>? = null,
    val periods: List<Period>
)