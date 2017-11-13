package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
@ScreenScope
class FriendsPresenter @Inject constructor(

) : BasePresenter<FriendsContract.View>(), FriendsContract.Presenter {

}