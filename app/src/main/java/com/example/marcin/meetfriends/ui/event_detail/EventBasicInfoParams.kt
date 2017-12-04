package com.example.marcin.meetfriends.ui.event_detail

import android.os.Bundle
import com.example.marcin.meetfriends.ui.event_detail.viewmodel.EventBasicInfo

/**
 * Created by marci on 2017-12-03.
 */
class EventBasicInfoParams(bundle: Bundle? = Bundle()) {

  val data: Bundle = bundle ?: Bundle()

  var event: EventBasicInfo
    get() = data.getParcelable(EVENT_INFO)
    set(value) = data.putParcelable(EVENT_INFO, value)

  constructor(event: EventBasicInfo) : this() {
    this.event = event
  }

  companion object {
    private const val EVENT_INFO = "eventInfo"
  }
}