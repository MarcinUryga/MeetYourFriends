package com.example.marcin.meetfriends.prefs

import android.content.Context
import timber.log.Timber

/**
 * Created by marci on 2017-12-30.
 */
class PrefsStorage(
    context: Context,
    name: String
) {

  private val prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE)

  fun put(key: String, value: Any?) {
    put(mapOf(key to value))
  }

  fun put(map: Map<String, Any?>) {
    val editor = prefs.edit()
    for ((key, value) in map) {
      if (value == null) {
        editor.remove(key)
      } else {
        when (value) {
          is Int -> editor.putInt(key, value)
          is Long -> editor.putLong(key, value)
          is Float -> editor.putFloat(key, value)
          is String -> editor.putString(key, value)
          is Boolean -> editor.putBoolean(key, value)
          is Double -> editor.putFloat(key, value.toFloat())
          else -> Timber.d("Unable to put $value under $key in shared preferences")
        }
      }
      editor.apply()
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun <T> get(key: String): T? = prefs.all[key] as? T

  fun getAll(): Map<String, Any?> = prefs.all.toMap()

  fun clear() {
    prefs.edit().clear().apply()
  }
}