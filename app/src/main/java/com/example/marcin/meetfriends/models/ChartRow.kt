package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2018-01-02.
 */
data class ChartRow(
    val timestamp: String,
    var voters: List<Voter>
)