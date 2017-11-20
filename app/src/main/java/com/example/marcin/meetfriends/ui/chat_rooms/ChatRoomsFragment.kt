package com.example.marcin.meetfriends.ui.chat_rooms

import android.content.Context
import com.example.marcin.meetfriends.mvp.BaseFragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created by marci on 2017-11-20.
 */
class ChatRoomsFragment : BaseFragment<ChatRoomsContract.Presenter>(), ChatRoomsContract.View {

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }
}