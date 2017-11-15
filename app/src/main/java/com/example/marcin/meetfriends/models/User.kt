package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-11-14.
 */

data class User(
    val uid: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val email: String? = null,
    val password: String? = null,
    val appDisplayName: String? = null,
    val firebaseToken: String? = null
)