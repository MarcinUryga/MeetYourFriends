package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.chat.viewmodel.Message
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-11-21.
 */
@ScreenScope
class ChatPresenter @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val eventIdParams: EventIdParams,
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
            .child(eventIdParams.eventId)
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
                  .child(eventIdParams.eventId)
                  .child(Constants.FIREBASE_CHAT)
                  .child(chatId), chat)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe()
      disposables?.add(disposable)
    }
  }
}