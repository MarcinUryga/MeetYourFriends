package com.example.marcin.meetfriends.ui.event_detail.event_questionnaire

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.example.marcin.meetfriends.ui.common.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.GetParticipantsUseCase
import com.example.marcin.meetfriends.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by marci on 2017-12-05.
 */
@ScreenScope
class EventQuestionnairePresenter @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val eventBasicInfoParams: EventBasicInfoParams,
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val auth: FirebaseAuth
) : BasePresenter<EventQuestionnaireContract.View>(), EventQuestionnaireContract.Presenter {

  override fun sendDateVote(selectedDate: DateTime) {
    val disposable = RxFirebaseDatabase
        .setValue(firebaseDatabase.reference
            .child(Constants.FIREBASE_EVENTS)
            .child(eventBasicInfoParams.event.id)
            .child(Constants.FIREBASE_QUESTIONNAIRE)
            .child(Constants.FIREBASE_DATE)
            .child(auth.currentUser?.uid.let { it!! }), selectedDate.millis.toString())
        .doFinally { view.showChosenDateSnackBar(selectedDate, auth.uid!!) }
        .subscribe()
    disposables?.add(disposable)
  }

  override fun removeChosenDateFromEvent(selectedDate: DateTime, userId: String) {
    firebaseDatabase.reference.child(Constants.FIREBASE_EVENTS)
        .child(eventBasicInfoParams.event.id)
        .child(Constants.FIREBASE_QUESTIONNAIRE)
        .child(Constants.FIREBASE_DATE)
        .orderByValue()
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