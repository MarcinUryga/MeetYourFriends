package com.example.marcin.meetfriends.ui.chat

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by marci on 2017-11-21.
 */
@ScreenScope
class ChatPresenter @Inject constructor(

) : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

}