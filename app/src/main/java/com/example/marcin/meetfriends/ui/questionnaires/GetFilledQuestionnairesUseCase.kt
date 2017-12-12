package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.models.DateQuestionnaire
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-12.
 */
class GetFilledQuestionnairesUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(): Maybe<MutableList<DateQuestionnaire>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(Constants.FIREBASE_QUESTIONNAIRE))
    { dataSnapshot ->
      val dateQuestionnaires = mutableListOf<DateQuestionnaire>()
      dataSnapshot.children.forEach {
        it.children.forEach {
          Timber.d(it.toString())
        }
      }
      return@observeSingleValueEvent dateQuestionnaires
    }
  }
}