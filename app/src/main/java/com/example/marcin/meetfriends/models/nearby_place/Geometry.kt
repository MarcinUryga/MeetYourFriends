package com.example.marci.googlemaps.pojo

import com.example.marcin.meetfriends.models.nearby_place.Viewport

/**
 * Created by marci on 2017-12-22.
 */
data class Geometry(
    val location: Location,
    val viewport: Viewport
)