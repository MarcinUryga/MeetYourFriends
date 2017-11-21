package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Chat
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
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
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

  override fun sendMessage(event: Event, text: String) {
    val chat = Chat(
        user = User(
            uid = auth.currentUser?.uid,
            displayName = auth.currentUser?.displayName,
            photoUrl = auth.currentUser?.photoUrl.toString()
        ),
        message = text
    )
    val disposable = RxFirebaseDatabase
        .setValue(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(event.id)
                .child(Constants.FIREBASE_CHAT), chat)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()
    disposables?.add(disposable)
    view.addMessage(chat)
  }
}