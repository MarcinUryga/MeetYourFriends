/*
package com.example.marcin.meetfriends.services

import com.example.marcin.meetfriends.ui.common.GetParticipantsUseCase
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import javax.inject.Inject

*/
/**
 * Created by marci on 2017-12-10.
 *//*

class UpdateDateTimeEventPresenter @Inject constructor(
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : UpdateDateTimeEventContract.Presenter {

  lateinit var disposable: Disposable

  override fun sendVote(*/
/*selectedDate: DateTime*//*
) {
*/
/*    disposable = getParticipantsUseCase.getParticipantsIds(eventBasicInfoParams.event.id.let { it!! })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { participantsIds ->
          val voter: Pair<String, String> = if (eventBasicInfoParams.event.organizerId == auth.currentUser?.uid) {
            Pair(Constants.FIREBASE_ORGANIZER_ID, auth.currentUser?.uid.let { it!! })
          } else {
            participantsIds.asSequence().first { it.value == auth.currentUser?.uid }.toPair()
          }
          val disposable = RxFirebaseDatabase
              .setValue(firebaseDatabase.reference
                  .child(Constants.FIREBASE_EVENTS)
                  .child(eventBasicInfoParams.event.id)
                  .child(Constants.FIREBASE_QUESTIONNAIRE)
                  .child(Constants.FIREBASE_DATE)
                  .child(selectedDate.millis.toString())
                  .child(voter.first), voter.second)
//              .doFinally { view.showChosenDateSnackBar(selectedDate, voter) }
              .subscribe()
        }*//*

  }

  override fun destroy() {
//    disposable.dispose()
  }

}*/
