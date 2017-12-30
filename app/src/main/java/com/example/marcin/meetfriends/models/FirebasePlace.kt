package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-12-30.
 */
data class FirebasePlace(
    val id: String? = null,
    val name: String? = null,
    val rating: Double? = null,
    val latLng: String? = null,
    val vicinity: String? = null,
    val photos: List<String> = emptyList()
)