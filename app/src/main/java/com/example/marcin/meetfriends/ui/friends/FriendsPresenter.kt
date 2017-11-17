package com.example.marcin.meetfriends.ui.friends

import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
@ScreenScope
class FriendsPresenter @Inject constructor(
    private val getFriendsFromFacebook: GetFriendsFromFacebook,
    private val getFriendsDromFirebase: GetFriendsFromFirebase
) : BasePresenter<FriendsContract.View>(), FriendsContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    /*getFriendsDromFirebase.get()
        .mergeWith { getFriendsFromFacebook.getFriends() }
        .subscribe({ users, profiles ->

          Timber.d(users.toString())
        }, { error ->
          Timber.e(error.localizedMessage)
        })*/
    getFriendsFromFacebook.getFriends()
        .subscribe({ profiles ->
          val friendsList = profiles.map {
            User(
                uid = it.id,
                displayName = it.name,
                photoUrl = it.getProfilePictureUri(170, 170).toString()
            )
          }
          view.showFriendsList(friendsList)
        })
  }

}