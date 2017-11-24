package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-11-21.
 */
data class Chat(
    val id: String? = null,
    val user: User? = null,
    val message: String? = null,
    val timestamp: String? = null,
    var ifMine: Boolean = false
)