package com.example.marcin.meetfriends.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Created by marci on 2017-12-05.
 */
object DateTimeFormatters {

  private val shortDateFormatter by lazy {
    DateTimeFormat.shortDate()
  }

  private val shortTimeFormatter by lazy {
    DateTimeFormat.shortTime()
  }

  fun formatToShortDate(dateTime: DateTime): String {
    return shortDateFormatter.print(dateTime)
  }

  fun formatToShortTime(dateTime: DateTime): String {
    return shortTimeFormatter.print(dateTime)
  }
}