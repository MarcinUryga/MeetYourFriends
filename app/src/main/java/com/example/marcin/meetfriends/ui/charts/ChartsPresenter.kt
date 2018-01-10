package com.example.marcin.meetfriends.ui.charts

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.*
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.params.EventIdParams
import com.example.marcin.meetfriends.ui.common.use_cases.GetFilledQuestionnairesUseCase
import com.example.marcin.meetfriends.ui.common.use_cases.GetFriendsFromFirebaseUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2018-01-02.
 */
@ScreenScope
class ChartsPresenter @Inject constructor(
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase,
    private val getFriendsFromFirebaseUseCase: GetFriendsFromFirebaseUseCase,
    private val getEventVenuesUseCase: GetEventVenuesUseCase,
    private val eventIdParams: EventIdParams
) : BasePresenter<ChartsContract.View>(), ChartsContract.Presenter {

  override fun resume() {
    super.resume()
    tryToLoadAllQuestionnaires()
  }

  private fun tryToLoadAllQuestionnaires() {
    val disposable = getFilledQuestionnairesUseCase.get(eventIdParams.eventId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ questionnaires ->
          if (questionnaires != -1) {
            tryToLoadDateQuestionnaires(questionnaires as Questionnaire)
            tryToLoadVenueQuestionnaires(questionnaires)
          } else {
            view.showNoDateVotes()
            view.showNoVenueVotes()
          }
        })
    disposables?.add(disposable)
  }

  private fun tryToLoadDateQuestionnaires(questionnaires: Questionnaire) {
    val dateQuestionnaires = questionnaires.dateQuestionnaire
    if (dateQuestionnaires != null) {
      createDateRowsByVotes(dateQuestionnaires.mapNotNull { it.value }.sortedBy { it.timestamp })
    } else {
      view.showNoDateVotes()
    }
  }

  private fun tryToLoadVenueQuestionnaires(questionnaires: Questionnaire) {
    val venueQuestionnaires = questionnaires.venueQuestionnaire
    if (venueQuestionnaires != null) {
      createVenueRowsByVotes(venueVotes = venueQuestionnaires.mapNotNull { it.value }.sortedBy { it.venueId })
    } else {
      view.showNoVenueVotes()
    }
  }

  private fun createDateRowsByVotes(dateVotes: List<DateVote>) {
    val chartRows = mutableListOf<DateRow>()
    var users = mutableListOf<Voter>()
    var chartRow = DateRow("", emptyList())
    dateVotes.forEach { dateVote ->
      if (isTheSameDateInVoting(chartRow, dateVote)) {
        chartRows.first { isRightDateRow(it, chartRow) }.voters = addUserToList(users, dateVote.userId.let { it!! })
      } else {
        users = mutableListOf(Voter(userId = dateVote.userId.let { it!! }))
        chartRow = DateRow(dateVote.timestamp.let { it!! }, users)
        chartRows.add(chartRow)
      }
    }
    getDateVoters(chartRows.sortedBy { it.timestamp })
  }

  private fun isTheSameDateInVoting(dateRow: DateRow, dateVote: DateVote) = dateRow.timestamp == dateVote.timestamp

  private fun isRightDateRow(dateRow: DateRow, chartRow: DateRow) = dateRow.timestamp == chartRow.timestamp

  private fun getDateVoters(dateRows: List<DateRow>) {
    val disposable = getFriendsFromFirebaseUseCase.get()
        .doFinally { view.hideDateQuestionnaireProgressBar() }
        .subscribe({ users ->
          dateRows.forEach { rows ->
            val voters = users.filter { user -> rows.voters.any { it.userId == user.uid } }
            rows.voters.forEach { voter ->
              voter.userPhotoUrl = voters.first { it.uid == voter.userId }.photoUrl.let { it!! }
            }
          }
          view.showDateQuestionnairesResult(dateRows)
        })
    disposables?.add(disposable)
  }

  private fun createVenueRowsByVotes(venueVotes: List<VenueVote>) {
    val chartRows = mutableListOf<VenueRow>()
    var users = mutableListOf<Voter>()
    var chartRow = VenueRow(FirebasePlace(), emptyList())
    venueVotes.forEach { venueVote ->
      if (isTheSameVenueInVoting(chartRow, venueVote)) {
        chartRows.first { isRightVenueRow(it, chartRow) }.voters = addUserToList(users, venueVote.userId.let { it!! })
      } else {
        users = mutableListOf(Voter(userId = venueVote.userId.let { it!! }))
        chartRow = VenueRow(FirebasePlace(id = venueVote.venueId.let { it!! }), users)
        chartRows.add(chartRow)
      }
    }
    getVenueVotersData(chartRows)
  }

  private fun isTheSameVenueInVoting(venueRow: VenueRow, venueVote: VenueVote) = venueRow.venue.id == venueVote.venueId

  private fun isRightVenueRow(venueRow: VenueRow, chartRow: VenueRow) = venueRow.venue.id == chartRow.venue.id

  private fun getVenueVotersData(venueRows: List<VenueRow>) {
    val disposable = getFriendsFromFirebaseUseCase.get()
        .subscribe({ users ->
          venueRows.forEach { rows ->
            val voters = users.filter { user -> rows.voters.any { it.userId == user.uid } }
            rows.voters.forEach { voter ->
              voter.userPhotoUrl = voters.first { it.uid == voter.userId }.photoUrl.let { it!! }
            }
          }
          getVenueData(venueRows)
        })
    disposables?.add(disposable)
  }

  private fun getVenueData(venueRows: List<VenueRow>) {
    val disposable = getEventVenuesUseCase.get(eventIdParams.eventId)
        .doFinally { view.hideVenueQuestionnaireProgressBar() }
        .subscribe({ venues ->
          val venuesList = venues.filter { venue -> venueRows.any { venue.id == it.venue.id } }
          venueRows.forEach { venueRow ->
            venueRow.venue = venuesList.first { it.id == venueRow.venue.id }
          }
          view.showVenueQuestionnairesResult(venueRows)
        })
    disposables?.add(disposable)
  }

  private fun addUserToList(users: MutableList<Voter>, userId: String): MutableList<Voter> {
    users.add(Voter(userId = userId))
    return users
  }
}
