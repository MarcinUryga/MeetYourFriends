package com.example.marcin.meetfriends.ui.questionnaires

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.Questionnaire
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsPresenter
import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.use_cases.GetFilledQuestionnairesUseCase
import com.example.marcin.meetfriends.ui.planned_event_detail.viewmodel.EventBasicInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseChildEvent
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
    private val auth: FirebaseAuth,
    firebaseDatabase: FirebaseDatabase,
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase
) : BaseLoadEventsPresenter<QuestionairesContract.View>(auth, firebaseDatabase), QuestionairesContract.Presenter {

  override fun resume() {
    super.resume()
    loadEvents()
  }

  override fun handleChosenEvent(clickEvent: Observable<Event>) {
    val disposable = clickEvent.subscribe({ event ->
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

  override fun manageEventItem(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    val disposable = getFilledQuestionnairesUseCase.get(dataSnapshot.key)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { view.hideNoEventsLayout() }
        .doFinally { view.hideLoadingProgressBar() }
        .subscribe({ questionnaires ->
          if (!isQuestionnaireExisting(questionnaires) || !isVotingFinishedForCurrentUser(questionnaires as Questionnaire)) {
            manageEvent(dataSnapshot)
          }
        }, { error ->
          Timber.d(error.localizedMessage)
        })
    disposables?.add(disposable)
  }

  private fun manageEvent(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) {
    if (isFinishedVoting(dataSnapshot)) {
      removeEvent(dataSnapshot)
    } else if (!isFinishedVoting(dataSnapshot)) {
      addEvent(dataSnapshot)
      view.hideNoEventsLayout()
    }
    if (view.getEventItemsSizeFromAdapter() == 0) {
      view.showNoEventsView()
    }
  }

  override fun isFinishedVoting(dataSnapshot: RxFirebaseChildEvent<DataSnapshot>) = dataSnapshot.value.getValue(Event::class.java)?.finishedVoting.let { it!! }

  private fun isQuestionnaireExisting(questionnaires: Any) = questionnaires != -1

  private fun isVotingFinishedForCurrentUser(questionnaires: Questionnaire): Boolean {
    val userDateVote = questionnaires.dateQuestionnaire?.map { it.value }?.firstOrNull { it.userId == auth.currentUser?.uid }
    val userVenueVote = questionnaires.venueQuestionnaire?.map { it.value }?.firstOrNull { it.userId == auth.currentUser?.uid }
    return userDateVote != null && userVenueVote != null
  }
}