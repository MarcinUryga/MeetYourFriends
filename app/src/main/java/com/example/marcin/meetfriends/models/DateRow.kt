package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2018-01-02.
 */
data class DateRow(
    val timestamp: String,
    var voters: List<Voter>
) : ChartRow