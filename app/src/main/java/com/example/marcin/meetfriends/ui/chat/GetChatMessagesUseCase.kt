package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Maybe
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-21.
 */
class GetChatMessagesUseCase @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {

  fun get(eventId: String): Maybe<MutableList<Chat>> {
    return RxFirebaseDatabase.observeSingleValueEvent(
        firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventId)
            .child(Constants.FIREBASE_CHAT)) { dataSnapshot ->
      val chatMessages = mutableListOf<Chat>()
      dataSnapshot.children.forEach {
        chatMessages.add(it.getValue(Chat::class.java).let { it!! })
      }
      return@observeSingleValueEvent chatMessages
    }
  }
}