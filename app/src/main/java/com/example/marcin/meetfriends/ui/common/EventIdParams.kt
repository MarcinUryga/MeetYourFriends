package com.example.marcin.meetfriends.ui.common

import android.os.Bundle

/**
 * Created by marci on 2017-11-29.
 */
class EventIdParams(bundle: Bundle? = Bundle()) {

    val data: Bundle = bundle ?: Bundle()

    var eventId: String
        get() = data.getString(EVENT_ID)
        set(value) = data.putString(EVENT_ID, value)

    constructor(eventId: String): this(){
        this.eventId = eventId
    }

    companion object {
        private const val EVENT_ID = "eventId"
    }
}