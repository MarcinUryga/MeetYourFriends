package com.example.marcin.meetfriends.ui.friends

import android.os.Bundle

/**
 * Created by marci on 2017-12-03.
 */
class ParticipantsListParams(bundle: Bundle? = Bundle()) {
  val data: Bundle = bundle ?: Bundle()

  var participantsIdList: ArrayList<String>
    get() = data.getStringArrayList(PARTICIPANTS_ID_LIST)
    set(value) = data.putStringArrayList(PARTICIPANTS_ID_LIST, value)

  constructor(participantsIdList: ArrayList<String>) : this() {
    this.participantsIdList = participantsIdList
  }

  companion object {
    private const val PARTICIPANTS_ID_LIST = "participantsIdList"
  }
}