package com.example.marcin.meetfriends.ui.event_detail

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

  fun getParticipantsIds(eventId: String): Maybe<MutableMap<String, String>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_PARTICIPANTS))
    { dataSnapshot ->
      val participantsIds = mutableMapOf<String, String>()
      dataSnapshot.children.forEach {
        participantsIds.put(it.key, it.getValue(String::class.java).let { it!! })
      }
      return@observeSingleValueEvent participantsIds
    }
  }

  fun getOrganizer(organizerId: String): Maybe<User> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference.child(Constants.FIREBASE_USERS))
    { dataSnapshot ->
      return@observeSingleValueEvent dataSnapshot.children
          .firstOrNull {
            it.key == organizerId
          }?.getValue(User::class.java).let { it!! }

    }
  }
}

