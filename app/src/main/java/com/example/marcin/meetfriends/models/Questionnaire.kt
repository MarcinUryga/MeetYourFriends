package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-12-30.
 */
data class Questionnaire(
    val dateQuestionnaire: HashMap<String, DateVote>? = null,
    val venueQuestionnaire: HashMap<String, VenueVote>? = null
)