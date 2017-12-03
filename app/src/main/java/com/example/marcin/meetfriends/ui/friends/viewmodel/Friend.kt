package com.example.marcin.meetfriends.ui.friends.viewmodel

/**
 * Created by marci on 2017-12-03.
 */
data class Friend(
    val id: String,
    val displayName: String,
    val photoUrl: String,
//    val phoneNumber: String?,
//    val email: String?,
    var isInvitated: Boolean = false
)