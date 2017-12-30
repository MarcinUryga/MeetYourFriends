package com.example.marcin.meetfriends.ui.search_venues

import com.example.marcin.meetfriends.prefs.PrefsManager
import com.example.marcin.meetfriends.prefs.PrefsManager.Companion.GOOGLE_PLACES_ID_PREFS
import javax.inject.Inject

/**
 * Created by marci on 2017-12-30.
 */
class VenuesStorage @Inject constructor(
    prefsManager: PrefsManager
) {

  private val storage = prefsManager.create(GOOGLE_PLACES_ID_PREFS)

  fun add(id: String, distance: String) {
    storage.put(id, distance)
  }

  fun remove(id: String) {
    storage.put(id, null)
  }

  fun getAll(): Map<String, String> {
    return storage.getAll() as Map<String, String>
  }

  fun removeAll() {
    storage.clear()
  }
}