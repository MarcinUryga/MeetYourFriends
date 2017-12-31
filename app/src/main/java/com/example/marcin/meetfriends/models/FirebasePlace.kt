package com.example.marcin.meetfriends.models

import com.example.marcin.meetfriends.models.nearby_place.Distance

/**
 * Created by marci on 2017-12-30.
 */
data class FirebasePlace(
    val id: String? = null,
    val name: String? = null,
    val latLng: String? = null,
    val rating: Double? = null,
    val vicinity: String? = null,
    val placeIcon: String? = null,
    var distance: Distance? = null,
    val photos: List<String> = emptyList()
)