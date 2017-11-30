package com.example.marcin.meetfriends.ui.event_detail

import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.MvpPresenter
import com.example.marcin.meetfriends.mvp.MvpView

/**
 * Created by marci on 2017-11-28.
 */
interface EventDetailContract {

    interface View : MvpView {

        fun showProgressBar()

        fun hideProgressBar()

        fun showEventDetails(event: Event)

        fun showParticipants(participants: List<User>)
    }

    interface Presenter : MvpPresenter
}