package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-11-21.
 */
data class Chat(
    val user: User? = null,
    val message: String? = null,
    val timestamp: String? = null
)