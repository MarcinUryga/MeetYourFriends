package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-12.
 */
@ScreenScope
class QuestionnairesPresenter @Inject constructor(
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : BasePresenter<QuestionairesContract.View>(), QuestionairesContract.Presenter {

  override fun resume() {
    super.resume()
    loadFilledQuestionnaires()
    loadChatRooms()
  }

  private fun loadChatRooms() {
//    view.hideRefresh()
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val organizerIdPath = dataSnapshot.value.child(Constants.FIREBASE_ORGANIZER_ID)
          if ((organizerIdPath.getValue(String::class.java) == auth.uid)) {
            addEvent(dataSnapshot)
          } else {
            findParticipants(dataSnapshot)
          }
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun findParticipants(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = RxFirebaseDatabase.observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS).child(dataSnapshot.key).child(Constants.FIREBASE_PARTICIPANTS))
        .subscribe({ participantsIdDataSnapshot ->
          val participantsIdPath = participantsIdDataSnapshot.value
          if (participantsIdPath.value == auth.uid) {
            addEvent(dataSnapshot)
          }
        })
    disposables?.add(disposable)
  }

  private fun addEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = getFilledQuestionnairesUseCase.get(dataSnapshot.key)
        .subscribe { filledQuestionnaires ->
          val questionnaire = filledQuestionnaires.firstOrNull { it.userId == auth.currentUser?.uid }
          if (questionnaire == null) {
            view.manageEvent(dataSnapshot)
          }
        }
    disposables?.add(disposable)
/*    if (loading) {
      view.hideLoading()
    }
    view.hideEmptyEvents()*/
  }

  fun loadFilledQuestionnaires() {
    val disposable = getFilledQuestionnairesUseCase.get("-L-kwk4xWbemCDGbsGpH").subscribe()
    disposables?.add(disposable)
  }
}