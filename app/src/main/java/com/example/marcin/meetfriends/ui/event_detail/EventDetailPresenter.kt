package com.example.marcin.meetfriends.ui.event_detail

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by marci on 2017-11-28.
 */
@ScreenScope
class EventDetailPresenter @Inject constructor(
        private val getEventDetailsUseCase: GetEventDetailsUseCase,
        private val getParticipantsUseCase: GetParticipantsUseCase,
        private val eventDetailsParams: EventDetailsParams
) : BasePresenter<EventDetailContract.View>(), EventDetailContract.Presenter {

    override fun resume() {
        super.resume()
        val disposable = getEventDetailsUseCase.get(eventDetailsParams.eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .doFinally { }
                .subscribe({ event ->
                    view.showEventDetails(event)
                    val disposable = getParticipantsUseCase.get(event.participants.map { it.value })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { view.showProgressBar() }
                            .doFinally { view.hideProgressBar() }
                            .subscribe({ participants ->
                                view.showParticipants(participants)
                            })
                    disposables?.add(disposable)
                })
        disposables?.add(disposable)
    }
}