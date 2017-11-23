package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-11-20.
 */
class GetEventsUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) {

  fun get(): Maybe<MutableList<Event>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
    { dataSnapshot ->
      val events = mutableListOf<Event>()
      dataSnapshot.children.filter {
        it.child(Constants.FIREBASE_ORGANIZER_ID).getValue(String::class.java) == auth.uid
      }.forEach {
        events.add(it.getValue(Event::class.java).let { it!! })
      }
      return@observeSingleValueEvent events
    }
  }
}