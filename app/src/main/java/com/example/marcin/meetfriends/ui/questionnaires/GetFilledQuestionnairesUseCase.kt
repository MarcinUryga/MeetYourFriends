package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.models.DateQuestionnaire
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

  fun get(eventId: String): Maybe<MutableList<DateQuestionnaire>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_QUESTIONNAIRE)
            .child(Constants.FIREBASE_DATE))
    { dataSnapshot ->
      val dateQuestionnaires = mutableListOf<DateQuestionnaire>()
      dataSnapshot.children.forEach {
        dateQuestionnaires.add(DateQuestionnaire(it.key, it.value.toString()))
      }
      return@observeSingleValueEvent dateQuestionnaires
    }
  }
}