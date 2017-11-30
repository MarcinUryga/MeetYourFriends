package com.example.marcin.meetfriends.models

import java.io.Serializable

/**
 * Created by marci on 2017-11-20.
 */
data class Event(
    val id: String? = null,
    val organizerId: String? = null,
    val name: String? = null,
    val date: String? = null,
    val description: String? = null,
    val participants: Map<String, String> = emptyMap()
) : Serializable