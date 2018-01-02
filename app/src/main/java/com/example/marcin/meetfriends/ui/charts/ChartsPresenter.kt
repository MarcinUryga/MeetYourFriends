package com.example.marcin.meetfriends.ui.charts

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.ChartRow
import com.example.marcin.meetfriends.models.DateVote
import com.example.marcin.meetfriends.models.Questionnaire
import com.example.marcin.meetfriends.models.Voter
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventIdParams
import com.example.marcin.meetfriends.ui.common.GetFilledQuestionnairesUseCase
import com.example.marcin.meetfriends.ui.common.GetFriendsFromFirebase
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2018-01-02.
 */
@ScreenScope
class ChartsPresenter @Inject constructor(
    private val getFilledQuestionnairesUseCase: GetFilledQuestionnairesUseCase,
    private val getFriendsFromFirebase: GetFriendsFromFirebase,
    private val firebaseDatabase: FirebaseDatabase,
    private val eventIdParams: EventIdParams
) : BasePresenter<ChartsContract.View>(), ChartsContract.Presenter {

  override fun resume() {
    super.resume()
    val disposable = getFilledQuestionnairesUseCase.get(eventIdParams.eventId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ questionnaires ->
          val chartRows = mutableListOf<ChartRow>()
          var users = mutableListOf<Voter>()
          if (questionnaires != -1) {
            val dateQuestionnaires = (questionnaires as Questionnaire).dateQuestionnaire?.mapNotNull { it.value }?.sortedBy { it.timestamp }
            var chartRow = ChartRow("", emptyList())
            dateQuestionnaires?.forEach { dateVote ->
              if (isTheSameDateInVoting(chartRow, dateVote)) {
                addUserToChartRow(users, dateVote, chartRows, chartRow)
              } else {
                users = mutableListOf(Voter(userId = dateVote.userId.let { it!! }))
                chartRow = ChartRow(dateVote.timestamp.let { it!! }, users)
                chartRows.add(chartRow)
              }
            }
            getVotersData(chartRows.sortedBy { it.timestamp })
          }
        })
    disposables?.add(disposable)
  }

  private fun getVotersData(chartRows: List<ChartRow>) {
    val disposable = getFriendsFromFirebase.get()
        .subscribe({ users ->
          chartRows.forEach { rows ->
            val voters = users.filter { user -> rows.voters.any { it.userId == user.uid } }
            rows.voters.forEach { voter ->
              voter.userPhotoUrl = voters.first { it.uid == voter.userId }.photoUrl.let { it!! }
            }
          }
          view.showQuestionnairesResult(chartRows)
        })
    disposables?.add(disposable)
  }

  private fun addUserToChartRow(users: MutableList<Voter>, dateVote: DateVote, chartRows: MutableList<ChartRow>, chartRow: ChartRow) {
    users.add(Voter(userId = dateVote.userId.let { it!! }))
    chartRows.first { it.timestamp == chartRow.timestamp }.voters = users
  }

  private fun isTheSameDateInVoting(chartRow: ChartRow, dateVote: DateVote) = chartRow.timestamp == dateVote.timestamp
}