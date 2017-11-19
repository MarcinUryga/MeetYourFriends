package com.example.marcin.meetfriends.ui.friends

import com.beltaief.reactivefb.requests.ReactiveRequest
import com.facebook.GraphResponse
import com.facebook.Profile
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import org.json.JSONException
import javax.inject.Inject

/**
 * Created by marci on 2017-11-16.
 */
class GetFriendsFromFacebook @Inject constructor(

) {
  fun getFriends(): Single<List<Profile>> {
    val bundleAsString = "picture.width(147).height(147),name,first_name"

    return ReactiveRequest.getFriends(bundleAsString)
        .map({ transform(it) })
  }

  private fun transform(response: GraphResponse): List<Profile> {
    val gson = GsonBuilder().create()
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