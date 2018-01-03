package com.example.marcin.meetfriends.ui.confirmed_event_detail

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.utils.Constants
import com.example.marcin.meetfriends.utils.Constants.FIREBASE_VENUES
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2018-01-03.
 */
class GetEventVenueUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(event: Event): Maybe<FirebasePlace> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(event.id)
            .child(FIREBASE_VENUES))
    { dataSnapshot ->
      val venues = mutableListOf<FirebasePlace>()
      dataSnapshot.children.forEach {
        venues.add(it.getValue(FirebasePlace::class.java).let { it!! })
      }
      return@observeSingleValueEvent venues.first { it.id == event.venue }
    }
  }
}