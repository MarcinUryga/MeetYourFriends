package com.example.marcin.meetfriends.ui.chat.viewmodel

import com.example.marcin.meetfriends.models.User

/**
 * Created by marci on 2017-12-07.
 */
data class Message(
    val user: User,
    val message: String? = null,
    val timestamp: String? = null,
    var ifMine: Boolean = false,
    var isDateInMessage: Boolean = false) {
}