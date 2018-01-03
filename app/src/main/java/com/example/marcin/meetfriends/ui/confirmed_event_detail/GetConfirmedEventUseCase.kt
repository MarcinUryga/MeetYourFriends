package com.example.marcin.meetfriends.ui.confirmed_event_detail

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2018-01-03.
 */
class GetConfirmedEventUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(eventId: String): Maybe<Event> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId))
    { dataSnapshot ->
      return@observeSingleValueEvent dataSnapshot.getValue(Event::class.java).let { it!! }
    }
  }
}