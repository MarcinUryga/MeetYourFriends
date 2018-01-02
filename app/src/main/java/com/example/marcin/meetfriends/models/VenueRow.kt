package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2018-01-02.
 */
data class VenueRow(
    var venue: FirebasePlace,
    override var voters: List<Voter>
) : ChartRow(voters)