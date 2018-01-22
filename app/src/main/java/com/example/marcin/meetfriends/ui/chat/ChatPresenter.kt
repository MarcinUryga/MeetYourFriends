package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.models.DateVote
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.use_cases.GetParticipantsUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val getEventBasicInfoParams: EventBasicInfoParams,
    private val auth: FirebaseAuth
) : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    view.setUpActionBarTitle(getEventBasicInfoParams.event.name.let { it!! })
    getMessages()
  }

  override fun sendMessage(text: String) {
    if (text.isNotEmpty()) {
      val chat = createMessageModel(text)
      val disposable = RxFirebaseDatabase
          .setValue(getEventChatFirebasePath().child(chat.id), chat)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .doOnComplete {
            val message = Message(
                user = User(uid = auth.uid, displayName = auth.currentUser?.displayName),
                ifMine = true,
                message = text
            )
            if (message.ifContainsDate()) {
              sendDateVote(message.transformDateHandlerToJodaTime())
            }
          }
          .subscribe()
      disposables?.add(disposable)
    }
  }

  private fun createMessageModel(text: String): Chat {
    return Chat(
        id = firebaseDatabase.reference.push().key,
        userId = auth.currentUser?.uid,
        message = text,
        timestamp = DateTime().millis
    )
  }

  private fun getMessages() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(getEventChatFirebasePath())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
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
    val disposable = getParticipantsUseCase.getUserById(chat.userId.let { it!! })
        .subscribe { user ->
          view.addMessage(Message(
              user = user,
              message = chat.message,
              ifMine = chat.ifMine,
              timestamp = chat.timestamp
          ))
        }
    disposables?.add(disposable)
  }

  private fun getEventChatFirebasePath(): DatabaseReference {
    return firebaseDatabase.reference
        .child(Constants.FIREBASE_EVENTS)
        .child(getEventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_CHAT)
  }

  override fun sendDateVote(selectedDate: DateTime) {
    val disposable = RxFirebaseDatabase
        .setValue(getEventQuestionnairePath().child(auth.currentUser?.uid.let { it!! }),
            DateVote(
                userId = auth.currentUser?.uid,
                timestamp = selectedDate.millis.toString())
        )
        .doFinally {
          view.showChosenDateSnackBar(selectedDate, auth.uid!!)
        }
        .subscribe()
    disposables?.add(disposable)
  }

  private fun getEventQuestionnairePath(): DatabaseReference {
    return firebaseDatabase.reference
        .child(Constants.FIREBASE_EVENTS)
        .child(getEventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_QUESTIONNAIRE)
        .child(Constants.FIREBASE_DATE_QUESTIONNAIRE)
  }

  override fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String) {
    getEventQuestionnairePath().orderByValue()
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            dataSnapshot.ref.child(dataSnapshot.children.firstOrNull { it.key == userId }?.key.toString()).removeValue()
          }

          override fun onCancelled(p0: DatabaseError?) {
            Timber.d("Canncelled remove participant with id $userId")
          }
        })
  }
}