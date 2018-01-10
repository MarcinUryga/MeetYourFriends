package com.example.marcin.meetfriends.ui.common.params

import android.os.Bundle

/**
 * Created by marci on 2017-11-29.
 */
class PlaceIdParams(bundle: Bundle? = Bundle()) {

  val data: Bundle = bundle ?: Bundle()

  var placeId: String
    get() = data.getString(PLACE_ID)
    set(value) = data.putString(PLACE_ID, value)

  constructor(placeId: String) : this() {
    this.placeId = placeId
  }

  companion object {
    private const val PLACE_ID = "placeId"
  }
}