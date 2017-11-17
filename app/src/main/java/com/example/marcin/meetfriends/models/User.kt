package com.example.marcin.meetfriends.models

import android.net.Uri

/**
 * Created by marci on 2017-11-14.
 */

data class User(
    val uid: String? = null,
    val facebookId: String? = null,
    val displayName: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val email: String? = null,
    val firebaseToken: String? = null
)