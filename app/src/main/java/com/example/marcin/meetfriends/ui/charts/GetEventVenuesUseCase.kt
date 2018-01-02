package com.example.marcin.meetfriends.ui.charts

import com.example.marcin.meetfriends.models.FirebasePlace
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2018-01-02.
 */
class GetEventVenuesUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(eventId: String): Maybe<List<FirebasePlace>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_VENUES))
    { dataSnapshot ->
      val venues = mutableListOf<FirebasePlace>()
      dataSnapshot.children.forEach {
        venues.add(it.getValue(FirebasePlace::class.java).let { it!! })
      }
      return@observeSingleValueEvent venues
    }
  }
}