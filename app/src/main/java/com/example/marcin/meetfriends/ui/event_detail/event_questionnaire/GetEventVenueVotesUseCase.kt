package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import com.example.marcin.meetfriends.models.VenueVote
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-12-30.
 */
class GetEventVenueVotesUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(eventId: String): Maybe<MutableList<VenueVote>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_QUESTIONNAIRE)
            .child(Constants.FIREBASE_VENUE_QUESTIONNAIRE))
    { dataSnapshot ->
      val venuesQuestionnaires = mutableListOf<VenueVote>()
      dataSnapshot.children.forEach {
        venuesQuestionnaires.add(VenueVote(it.key, it.value.toString()))
      }
      return@observeSingleValueEvent venuesQuestionnaires
    }
  }
}