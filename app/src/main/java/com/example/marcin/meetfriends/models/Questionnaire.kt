package com.example.marcin.meetfriends.models

/**
 * Created by marci on 2017-12-30.
 */
data class Questionnaire(
    val date: List<DateQuestionnaire>? = null,
    val venues: List<VenueQuestionnaire>? = null
)