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

  private val fullDatesPattern: Pattern
  private val datesAsWordPattern: Pattern
  var dateHandler: String? = null
    get() = field

  init {
    FULL_PL_DATES_REGEX.toRegex()
    DATES_AS_WORDS_REGEX.toRegex()
    fullDatesPattern = Pattern.compile(FULL_PL_DATES_REGEX)
    datesAsWordPattern = Pattern.compile(DATES_AS_WORDS_REGEX)
  }

  fun ifContainsDate(): Boolean {
    val fullDatesMatcher = fullDatesPattern.matcher(message)
    val datesAsWordMatcher = datesAsWordPattern.matcher(message)
    if (fullDatesMatcher.matches()) {
      dateHandler = fullDatesMatcher.group(1)
      return true
    } else if (datesAsWordMatcher.matches()) {

    }
    return false
  }

  fun transformDateHandlerToJodaTime(): DateTime {
    var dateTab = dateHandler?.split(" ").let { it!! }
    val timeTab = dateTab[0].split(":")
    return when {
      dateTab[1].contains("/") -> convertToDateTime("/", dateTab, timeTab)
      dateTab[1].contains(".") -> convertToDateTime(".", dateTab, timeTab)
      dateTab[1].contains("-") -> convertToDateTime("-", dateTab, timeTab)
      else -> DateTime(dateTab[3].toInt(), repackMonth(dateTab[2]), dateTab[1].toInt(), timeTab[0].toInt(),timeTab[1].toInt())
    }
  }

  private fun convertToDateTime(separator: String, dateTab: List<String>, timeTab: List<String>): DateTime {
    val date = dateTab[1].split(separator)
    return DateTime(date[2].toInt(), repackMonth(date[1]), date[0].toInt(), timeTab[0].toInt(), timeTab[1].toInt())
  }

  private fun repackMonth(month: String): Int {
    return when {
      Months.JANUARY.polishName.matches(month) -> 1
      Months.FEBRUARY.polishName.matches(month) -> 2
      Months.MARCH.polishName.matches(month) -> 3
      Months.APRIL.polishName.matches(month) -> 4
      Months.MAY.polishName.matches(month) -> 5
      Months.JUNE.polishName.matches(month) -> 6
      Months.JULY.polishName.matches(month) -> 7
      Months.AUGUST.polishName.matches(month) -> 8
      Months.SEPTEMBER.polishName.matches(month) -> 9
      Months.OCTOBER.polishName.matches(month) -> 10
      Months.NOVEMBER.polishName.matches(month) -> 11
      Months.DECEMBER.polishName.matches(month) -> 12
      else -> month.toInt()
    }
  }


  companion object {
    //    private val FULL_ENG_DATES_REGEX: String = "^(\\d|(\\d\\d))( |.|/|-)(([jJ]an|[fF]eb|[mM]ar|[aA]pr|[mM]ay|[jJ]un|[jJ]ul|[aA]ug|[sS]ep|[oO]ct|[nN]ov|[dD]ec)|([1-9]|(1[0-2])))( |.|/|-)((20\\d\\d))"
    private val FULL_PL_DATES_REGEX: String = ".*((\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d)) ((\\d|(\\d\\d))( |.|/|-)(([sS]tycze[nń]|[lL]uty|[mM]arzec|[kK]wiecie[nń]|[mM]aj|[cC]erwiec|[lL]ipiec|[sS]ierpie[nń]|[wW]rzesie[nń]|[pP]a[zź]dziernik|[lL]istopad|[gG]rudzie[nń])|([1-9]|(1[0-2])))( |.|/|-)((20\\d\\d)))).*"
    private val DATES_AS_WORDS_REGEX: String = "([dD]zi[sś](|iaj))|([jJ]utro)|([Pp]ojutrze)|([zZ]a tydzień)|([zZ]a miesi[ąa]c)"
//    private val TIME_REGEX: String = "(\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d))"
  }

  enum class Months(val polishName: Regex) {
    JANUARY("[Ss]tycze[nń]".toRegex()),
    FEBRUARY("[lL]uty".toRegex()),
    MARCH("[mM]arzec".toRegex()),
    APRIL("[kK]wiecie[nń]".toRegex()),
    MAY("[mM]aj".toRegex()),
    JUNE("[cC]erwiec".toRegex()),
    JULY("[lL]ipiec".toRegex()),
    AUGUST("[sS]ierpie[nń]".toRegex()),
    SEPTEMBER("[wW]rzesie[nń]".toRegex()),
    OCTOBER("[pP]a[zź]dziernik".toRegex()),
    NOVEMBER("[lL]istopad".toRegex()),
    DECEMBER("[gG]rudzie[nń]".toRegex())
  }
}