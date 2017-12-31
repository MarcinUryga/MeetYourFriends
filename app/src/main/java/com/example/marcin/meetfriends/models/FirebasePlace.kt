package com.example.marcin.meetfriends.models

import com.example.marcin.meetfriends.models.nearby_place.Distance

/**
 * Created by marci on 2017-12-30.
 */
data class FirebasePlace(
    val id: String? = null,
    val name: String? = null,
    val rating: Double? = null,
    val latLng: String? = null,
    val vicinity: String? = null,
    var distance: Distance? = Distance("0", 0),
    val photos: List<String> = emptyList()
)