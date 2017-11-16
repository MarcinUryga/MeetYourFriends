package com.example.marcin.meetfriends.models

import android.net.Uri

/**
 * Created by marci on 2017-11-14.
 */

data class User(
    val uid: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: Uri? = null,
    val email: String? = null,
    val password: String? = null,
    val firebaseToken: String? = null
)