package com.example.marcin.meetfriends.ui.change_event

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-11-27.
 */
class GetMyEventsUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) {

  fun get(): Maybe<List<Event>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
    { dataSnapshot ->
      val events = mutableListOf<Event>()
      dataSnapshot.children.forEach {
        events.add(it.getValue(Event::class.java).let { it!! })
      }
      return@observeSingleValueEvent events.filter { it.organizerId == auth.uid }
    }
  }
}