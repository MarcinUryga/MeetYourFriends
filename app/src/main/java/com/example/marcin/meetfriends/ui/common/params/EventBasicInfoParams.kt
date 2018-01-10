package com.example.marcin.meetfriends.ui.common.params

import android.os.Bundle
import com.example.marcin.meetfriends.ui.planned_event_detail.viewmodel.EventBasicInfo

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
    const val EVENT_INFO = "eventInfo"
  }
}