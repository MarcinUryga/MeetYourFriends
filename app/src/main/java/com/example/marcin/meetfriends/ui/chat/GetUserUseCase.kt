package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-12-07.
 */
class GetUserUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(userId: String): Maybe<User> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference.child(Constants.FIREBASE_USERS))
    { dataSnapshot ->
      return@observeSingleValueEvent dataSnapshot.children
          .firstOrNull {
            it.key == userId
          }?.getValue(User::class.java).let { it!! }

    }
  }
}