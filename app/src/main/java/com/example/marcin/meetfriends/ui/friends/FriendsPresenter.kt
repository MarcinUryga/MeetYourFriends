package com.example.marcin.meetfriends.ui.friends

import com.beltaief.reactivefb.requests.ReactiveRequest
import com.example.marcin.meetfriends.di.ScreenScope
import com.example.marcin.meetfriends.models.User
import com.example.marcin.meetfriends.mvp.BasePresenter
import com.facebook.GraphResponse
import com.facebook.Profile
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import org.json.JSONException
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by MARCIN on 2017-11-13.
 */
@ScreenScope
class FriendsPresenter @Inject constructor(
) : BasePresenter<FriendsContract.View>(), FriendsContract.Presenter {

  override fun onViewCreated() {
    super.onViewCreated()
    getFriends()
  }

  private fun getFriends(): Observable<List<User>>? {
    val bundleAsString = "picture.width(147).height(147),name,first_name"

    ReactiveRequest.getFriends(bundleAsString)
        .map({ transform(it) })
        .subscribe({ profiles ->
          val friendsList = profiles.map {
            User(
                uid = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                photoUrl = it.getProfilePictureUri(170, 170)
            )
          }
          view.showFriendsList(friendsList)
        }, { error ->
          Timber.d(error.localizedMessage)
        })
    return null
  }

  private fun transform(response: GraphResponse): List<Profile> {
    val gson = GsonBuilder()
//        .registerTypeAdapter(Date::class.java, GsonDateTypeAdapter())
        .create()
    var data: String? = null
    try {
      data = if (response.jsonObject.has("data"))
        response.jsonObject.get("data").toString()
      else
        response.jsonObject.toString()
    } catch (e: JSONException) {
      e.printStackTrace()
    }

    val listType = object : TypeToken<List<Profile>>() {

    }.type
    return gson.fromJson<List<Profile>>(data, listType)
  }
}