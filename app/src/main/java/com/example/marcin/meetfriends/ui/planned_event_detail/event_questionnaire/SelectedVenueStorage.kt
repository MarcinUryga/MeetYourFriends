package com.example.marcin.meetfriends.ui.planned_event_detail.event_questionnaire

import com.example.marcin.meetfriends.prefs.PrefsManager
import com.example.marcin.meetfriends.prefs.PrefsManager.Companion.SELECTED_VENUES
import javax.inject.Inject

/**
 * Created by marci on 2018-01-03.
 */
class SelectedVenueStorage @Inject constructor(
    prefsManager: PrefsManager
) {

  private val storage = prefsManager.create(SELECTED_VENUES)

  fun add(eventId: String, venueId: String) {
    storage.put(eventId, venueId)
  }

  fun remove(eventId: String) {
    storage.put(eventId, null)
  }

  fun get(eventId: String) = storage.get<String>(eventId)
}