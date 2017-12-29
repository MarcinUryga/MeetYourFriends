package com.example.marcin.meetfriends.models.nearby_place

import com.google.gson.annotations.SerializedName

/**
 * Created by marci on 2017-12-29.
 */
data class Review(
    @SerializedName("author_name")
    val authorName: String,
    @SerializedName("author_url")
    val authorUrl: String,
    val language: String,
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String,
    val rating: Int,
    @SerializedName("relative_time_description")
    val relativeTimeDescription: String,
    val text: String,
    val time: Int
)