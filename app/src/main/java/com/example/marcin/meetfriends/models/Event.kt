package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-11-20.
 */
data class Event(
    val id: String? = null,
    val organizerId: String? = null,
    val name: String? = null,
    val users: Map<String, User>? = emptyMap()
)