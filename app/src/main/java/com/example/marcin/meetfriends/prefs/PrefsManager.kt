package com.example.marcin.meetfriends.prefs

import com.example.marcin.meetfriends.MeetFriendsApplication

/**
 * Created by marci on 2017-12-30.
 */
class PrefsManager(
    private val application: MeetFriendsApplication
) {

  fun create(name: String) = PrefsStorage(application, name)

  fun createDefault() = create(APP_PREFS)

  companion object {
    const val APP_PREFS = "app_prefs"
    const val SELECTED_VENUES = "selected_venues"
    const val GOOGLE_PLACES_ID_PREFS = "google_places_id_prefs"
  }
}