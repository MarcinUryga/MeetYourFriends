package com.example.marcin.meetfriends.models.nearby_place

import android.location.Location

/**
 * Created by marci on 2017-12-28.
 */
data class Viewport(
    val northeast: Location,
    val southwest: Location
)