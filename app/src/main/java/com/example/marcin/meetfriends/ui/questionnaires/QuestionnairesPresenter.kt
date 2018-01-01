package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.Questionnaire
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.EventBasicInfo
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Observable
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
    loadUnfilledQuestionnaires()
  }

  override fun handleChosenEventRoom(chosenEventRoom: Observable<Event>) {
    val disposable = chosenEventRoom.subscribe({ event ->
      view.startEventQuestionnaireFragment(EventBasicInfoParams(
          event = EventBasicInfo(
              id = event.id,
              iconId = event.iconId,
              organizerId = event.organizerId,
              name = event.name,
              description = event.description
          )
      ))
    })
    disposables?.add(disposable)
  }

  private fun loadUnfilledQuestionnaires() {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ dataSnapshot ->
          val organizerIdPath = dataSnapshot.value.child(Constants.FIREBASE_ORGANIZER_ID)
          if ((organizerIdPath.getValue(String::class.java) == auth.uid)) {
            tryToAddEvent(dataSnapshot)
          } else {
            findParticipants(dataSnapshot)
          }
        }, { error ->
          Timber.e(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun findParticipants(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = RxFirebaseDatabase
        .observeChildEvent(
            firebaseDatabase.reference
                .child(Constants.FIREBASE_EVENTS)
                .child(dataSnapshot.key)
                .child(Constants.FIREBASE_PARTICIPANTS)
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ participantsIdDataSnapshot ->
          val participantsIdPath = participantsIdDataSnapshot.value
          if (participantsIdPath.value == auth.uid) {
            tryToAddEvent(dataSnapshot)
          }
        })
    disposables?.add(disposable)
  }

  private fun tryToAddEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = getFilledQuestionnairesUseCase.get(dataSnapshot.key)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.showLoading() }
        .doFinally { view.hideLoading() }
        .subscribe({ questionnaires ->
          if (!isQuestionnaireExisting(questionnaires)) {
            addEvent(dataSnapshot)
          } else {
            if (!isVotingFinishedForCurrentUser(questionnaires as Questionnaire)) {
              addEvent(dataSnapshot)
            } else if (view.getEventItemsSizeFromAdapter() == 0) {
              view.showEmptyQuestionnairesToFillScreen()
            }
          }
        }, { error ->
          Timber.d(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun addEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    view.manageEvent(dataSnapshot)
    view.hideEmptyQuestionnairesToFillScreen()
  }

  private fun isQuestionnaireExisting(questionnaires: Any) = questionnaires != -1

  private fun isVotingFinishedForCurrentUser(questionnaires: Questionnaire): Boolean {
    val userDateVote = questionnaires.dateQuestionnaire?.map { it.value }?.firstOrNull { it.userId == auth.currentUser?.uid }
    val userVenueVote = questionnaires.venueQuestionnaire?.map { it.value }?.firstOrNull { it.userId == auth.currentUser?.uid }
    return userDateVote != null && userVenueVote != null
  }
}