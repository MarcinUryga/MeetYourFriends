package com.example.marcin.meetfriends.storage

import android.content.SharedPreferences

/**
 * Created by marci on 2017-11-19.
 */
class SharedPref(
    private val sharedPreferences: SharedPreferences
) {

  private val storage = sharedPreferences.edit()

  fun saveChosenEvent(eventId: String) {
    storage.putString(CURRENT_EVENT_ID, eventId).apply()
  }

  fun getChosenEvent(): String {
    return sharedPreferences.getString(CURRENT_EVENT_ID, " ")
  }

  fun clearSharedPref() {
    storage.clear().apply()
  }

  companion object {
    const val CURRENT_EVENT_ID = "currentEvent"
  }
}