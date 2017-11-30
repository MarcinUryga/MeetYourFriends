package com.example.marcin.meetfriends.ui.event_detail

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by marci on 2017-11-29.
 */
class GetEventDetailsUseCase @Inject constructor(
        private val firebaseDatabase: FirebaseDatabase
) {

    fun get(eventId: String): Maybe<Event> {
        return RxFirebaseDatabase.observeSingleValueEvent(
                firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
        { dataSnapshot ->
            return@observeSingleValueEvent dataSnapshot.children
                    .first { it.key == eventId }
                    .getValue(Event::class.java)
                    .let { it!! }
        }
    }
}