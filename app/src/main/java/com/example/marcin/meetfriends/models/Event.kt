package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-11-20.
 */
data class Event(
    val id: String? = null,
    val iconId: String? = null,
    val organizerId: String? = null,
    val name: String? = null,
    var date: String? = null,
    var venue: String? = null,
    var description: String? = null,
    val participants: Map<String, String> = emptyMap(),
    val venues: List<FirebasePlace> = emptyList()
)