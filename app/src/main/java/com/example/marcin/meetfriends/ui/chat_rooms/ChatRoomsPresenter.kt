package com.example.marcin.meetfriends.ui.chat_rooms

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by marci on 2017-11-20.
 */
@ScreenScope
class ChatRoomsPresenter @Inject constructor(

) : BasePresenter<ChatRoomsContract.View>(), ChatRoomsContract.Presenter {

}