package com.example.marcin.meetfriends.ui.event_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.Event
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BaseActivity
import com.example.marcin.meetfriends.ui.event_detail.adapter.ParticipantsAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.content_event_details.*

/**
 * Created by marci on 2017-11-28.
 */
class EventDetailActivity : BaseActivity<EventDetailContract.Presenter>(), EventDetailContract.View {

    private lateinit var participantsAdapter: ParticipantsAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        participantsRecyclerView.layoutManager = GridLayoutManager(baseContext, 3)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showEventDetails(event: Event) {
        prepareToolbar(event.name)
        prepareActivityContent(event.description)
    }

    private fun prepareActivityContent(description: String?) {
        eventDescriptionTextView.text = description
    }

    private fun prepareToolbar(name: String?) {
        toolbar.title = name
        setSupportActionBar(toolbar)
    }

    override fun showParticipants(participants: List<User>) {
        participantsAdapter = ParticipantsAdapter(participants)
        participantsRecyclerView.adapter = participantsAdapter
    }

    companion object {
        fun newIntent(context: Context, params: EventDetailsParams): Intent {
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtras(params.data)
            return intent
        }
    }
}