package com.example.marcin.meetfriends.ui.common

import com.example.marcin.meetfriends.models.Questionnaire
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-12-12.
 */
class GetFilledQuestionnairesUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(eventId: String): Maybe<Any> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_QUESTIONNAIRE))
    { dataSnapshot ->
      return@observeSingleValueEvent dataSnapshot.getValue(Questionnaire::class.java) ?: -1
    }
  }
}