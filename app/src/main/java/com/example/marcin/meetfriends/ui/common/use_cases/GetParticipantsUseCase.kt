package com.example.marcin.meetfriends.ui.common.use_cases

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-11-29.
 */
class GetParticipantsUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun getParticipantsByIds(participantsIds: List<String>): Maybe<List<User>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_USERS))
    { dataSnapshot ->
      val eventParticipants = mutableListOf<User>()
      dataSnapshot.children
          .filter { user ->
            participantsIds.any { user.key == it }
          }
          .forEach {
            eventParticipants.add(it.getValue(User::class.java).let { it!! })
          }
      return@observeSingleValueEvent eventParticipants
    }
  }

  fun getUserById(userId: String): Maybe<User> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_USERS)
            .child(userId))
    { dataSnapshot ->
      return@observeSingleValueEvent dataSnapshot.getValue(User::class.java).let { it!! }
    }
  }
}

