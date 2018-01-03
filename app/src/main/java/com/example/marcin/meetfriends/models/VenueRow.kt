package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2018-01-02.
 */
data class VenueRow(
    var venue: FirebasePlace,
    var voters: List<Voter>
) : ChartRow