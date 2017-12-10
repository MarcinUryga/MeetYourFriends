package com.example.marcin.meetfriends.ui.chat.viewmodel

import com.example.marcin.meetfriends.models.User
import org.joda.time.DateTime
import java.util.regex.Pattern

/**
 * Created by marci on 2017-12-07.
 */
data class Message(
    val user: User,
    val message: String? = null,
    val timestamp: String? = null,
    var ifMine: Boolean = false,
    var isDateInMessage: Boolean = false) {

  private val pattern: Pattern
  var dateHandler: String? = null
    get() = field

  init {
    FULL_PL_DATES_REGEX.toRegex()
    DATES_AS_WORDS_REGEX.toRegex()
    pattern = Pattern.compile(FULL_PL_DATES_REGEX)
  }

  fun ifContainsDate(): Boolean {
    val matcher = pattern.matcher(message)
    if (matcher.matches()) {
      dateHandler = matcher.group(1)
      return true
    }
    return false
  }

  fun transformDateHandlerToJodaTime(): DateTime {
    val dateTab = dateHandler?.split(" ").let { it!! }
    return DateTime(dateTab[2].toInt(), dateTab[1].toInt(), dateTab[0].toInt(), 0, 0)
  }

  companion object {
    private val FULL_ENG_DATES_REGEX: String = "^(\\d|(\\d\\d))( |.|/|-)(([jJ]an|[fF]eb|[mM]ar|[aA]pr|[mM]ay|[jJ]un|[jJ]ul|[aA]ug|[sS]ep|[oO]ct|[nN]ov|[dD]ec)|([1-9]|(1[0-2])))( |.|/|-)((20\\d\\d))"
    private val FULL_PL_DATES_REGEX: String = ".* ((\\d|(\\d\\d))( |.|/|-)(([sS]tycze[nń]|[lL]uty|[mM]arzec|[kK]wiecie[nń]|[mM]aj|[cC]erwiec|[lL]ipiec|[sS]ierpie[nń]|[wW]rzesie[nń]|[pP]a[zź]dziernik|[lL]istopad|[gG]rudzie[nń])|([1-9]|(1[0-2])))( |.|/|-)((20\\d\\d))).*"
    private val DATES_AS_WORDS_REGEX: String = "([dD]zi[sś](|iaj))|([jJ]utro)|([Pp]ojutrze)|([zZ]a tydzień)"
  }
}