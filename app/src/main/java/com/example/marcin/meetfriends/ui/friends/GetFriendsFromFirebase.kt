package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-11-17.
 */
class GetFriendsFromFirebase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(): Maybe<MutableList<User>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference.child(Constants.FIREBASE_USERS))
    { dataSnapshot ->
      val users = mutableListOf<User>()
      dataSnapshot.children.forEach {
        users.add(it.getValue(User::class.java).let { it!! })
      }
      return@observeSingleValueEvent users
    }
  }
}