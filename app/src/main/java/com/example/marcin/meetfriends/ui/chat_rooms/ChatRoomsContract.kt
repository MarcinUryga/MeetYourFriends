package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.ui.common.params.EventBasicInfoParams
import com.example.marcin.meetfriends.ui.common.base_load_events_mvp.BaseLoadEventsContract

/**
 * Created by marci on 2017-11-20.
 */
interface ChatRoomsContract {

  interface View : BaseLoadEventsContract.View {

    fun startChatRoomActivity(params: EventBasicInfoParams)
  }

  interface Presenter : BaseLoadEventsContract.Presenter
}