package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.GetParticipantsUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-11-21.
 */
@ScreenScope
class ChatPresenter @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val getEventBasicInfoParams: EventBasicInfoParams,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val auth: FirebaseAuth
) : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    getMessages()
  }

  private fun getMessages() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(getEventBasicInfoParams.event.id)
            .child(Constants.FIREBASE_CHAT))
        .subscribe({ dataSnapshot ->
          val chatMessages = mutableListOf<Chat>()
          chatMessages.add(dataSnapshot.value.getValue(Chat::class.java).let { it!! })
          if (chatMessages.isNotEmpty()) {
            chatMessages.forEach {
              if (it.userId == auth.uid) {
                it.ifMine = true
              }
              createMessage(it)
            }
          }
        })
    disposables?.add(disposable)
  }

  private fun createMessage(chat: Chat) {
    val disposable = getUserUseCase.get(chat.userId.let { it!! })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { user ->
          view.addMessage(Message(
              user = user,
              message = chat.message,
              ifMine = chat.ifMine
          ))
        }
    disposables?.add(disposable)
  }

  override fun sendMessage(text: String) {
    if (text.isNotEmpty()) {
      val chatId = firebaseDatabase.reference.push().key
      val chat = Chat(
          id = chatId,
          userId = auth.currentUser?.uid,
          message = text
      )
      val disposable = RxFirebaseDatabase
          .setValue(
              firebaseDatabase.reference
                  .child(Constants.FIREBASE_EVENTS)
                  .child(getEventBasicInfoParams.event.id)
                  .child(Constants.FIREBASE_CHAT)
                  .child(chatId), chat)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnComplete {
            view.tryToVoteOnEventDate(Message(
                User(
                    uid = auth.uid,
                    displayName = auth.currentUser?.displayName
                ),
                ifMine = true,
                message = text
            ))
          }
          .subscribe()
      disposables?.add(disposable)
    }
  }

  override fun sendDateVote(selectedDate: DateTime) {
    val disposable = getParticipantsUseCase.getParticipantsIds(getEventBasicInfoParams.event.id.let { it!! })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { participantsIds ->
          val voter: Pair<String, String> = if (getEventBasicInfoParams.event.organizerId == auth.currentUser?.uid) {
            Pair(Constants.FIREBASE_ORGANIZER_ID, auth.currentUser?.uid.let { it!! })
          } else {
            participantsIds.asSequence().first { it.value == auth.currentUser?.uid }.toPair()
          }
          val disposable = RxFirebaseDatabase
              .setValue(firebaseDatabase.reference
                  .child(Constants.FIREBASE_EVENTS)
                  .child(getEventBasicInfoParams.event.id)
                  .child(Constants.FIREBASE_QUESTIONNAIRE)
                  .child(Constants.FIREBASE_DATE)
                  .child(selectedDate.millis.toString())
                  .child(voter.first), voter.second)
              .doFinally { view.showChosenDateSnackBar(selectedDate, voter) }
              .subscribe()
          disposables?.add(disposable)
        }
    disposables?.add(disposable)
  }

  override fun removeChosenDateFromEvent(selectedDate: DateTime, voter: Pair<String, String>) {
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
        .child(getEventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_QUESTIONNAIRE)
        .child(Constants.FIREBASE_DATE)
        .child(selectedDate.millis.toString())
        .orderByValue()
        .equalTo(voter.second)
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            dataSnapshot.ref.child(dataSnapshot.children.firstOrNull { it.value == voter.second }?.key.toString()).removeValue()
          }

          override fun onCancelled(p0: DatabaseError?) {
            Timber.d("Canncelled remove participant with id ${voter.second}")
          }
        })
  }
}