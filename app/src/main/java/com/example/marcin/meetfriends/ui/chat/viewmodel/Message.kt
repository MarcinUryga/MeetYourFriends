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

  private val fullDatesDotPattern: Pattern
  private val fullDatesSlashPattern: Pattern
  private val fullDatesDashPattern: Pattern
  private val fullDatesWhitespacePattern: Pattern
  private val datesAsWordPattern: Pattern

  private val currentDate = DateTime()

  var dateHandler: String? = null
    get() = field

  init {
    FULL_PL_DATES_DOT_SEPARATE_REGEX.toRegex()
    FULL_PL_DATES_SLASH_SEPARATE_REGEX.toRegex()
    FULL_PL_DATES_DASH_SEPARATE_REGEX.toRegex()
    FULL_PL_DATES_WHITESPACE_SEPARATE_REGEX.toRegex()

    DATES_AS_WORDS_REGEX.toRegex()
    fullDatesDotPattern = Pattern.compile(FULL_PL_DATES_DOT_SEPARATE_REGEX)
    fullDatesSlashPattern = Pattern.compile(FULL_PL_DATES_SLASH_SEPARATE_REGEX)
    fullDatesDashPattern = Pattern.compile(FULL_PL_DATES_DASH_SEPARATE_REGEX)
    fullDatesWhitespacePattern = Pattern.compile(FULL_PL_DATES_WHITESPACE_SEPARATE_REGEX)
    datesAsWordPattern = Pattern.compile(DATES_AS_WORDS_REGEX)
  }

  fun ifContainsDate(): Boolean {
    val fullDatesDotMatcher = fullDatesDotPattern.matcher(message)
    val fullDatesSlashMatcher = fullDatesSlashPattern.matcher(message)
    val fullDatesDashMatcher = fullDatesDashPattern.matcher(message)
    val fullDatesWhitespaceMatcher = fullDatesWhitespacePattern.matcher(message)
    val datesAsWordMatcher = datesAsWordPattern.matcher(message)
    when {
      fullDatesDotMatcher.matches() -> dateHandler = fullDatesDotMatcher.group(1)
      fullDatesSlashMatcher.matches() -> dateHandler = fullDatesSlashMatcher.group(1)
      fullDatesDashMatcher.matches() -> dateHandler = fullDatesDashMatcher.group(1)
      fullDatesWhitespaceMatcher.matches() -> dateHandler = fullDatesWhitespaceMatcher.group(1)
      datesAsWordMatcher.matches() -> dateHandler = datesAsWordMatcher.group(1)
    }
    return dateHandler != null
  }

  fun transformDateHandlerToJodaTime(): DateTime {
    var dateTab = dateHandler?.split(" ").let { it!! }
    val timeTab = dateTab[0].split(":")
    return when {
      dateTab[1].contains("/") -> convertToDateTime("/", dateTab, timeTab)
      dateTab[1].contains(".") -> convertToDateTime(".", dateTab, timeTab)
      dateTab[1].contains("-") -> convertToDateTime("-", dateTab, timeTab)
      !dateTab[1].contains("[0-9]".toRegex()) && dateTab.size == 3 -> DateItems.createDateFromOthersWords("${dateTab[1]} ${dateTab[2]}", timeTab)
      !dateTab[1].contains("[0-9]".toRegex()) -> DateItems.createDateFromOthersWords(dateTab[1], timeTab)
      else -> DateTime(convertToYear(dateTab, 4), DateItems.repackMonth(dateTab[2]), dateTab[1].toInt(), timeTab[0].toInt(), timeTab[1].toInt())
    }
  }

  private fun convertToDateTime(separator: String, dateTab: List<String>, timeTab: List<String>): DateTime {
    val date = dateTab[1].split(separator)
    val year = convertToYear(date)
    return DateTime(year, DateItems.repackMonth(date[1]), date[0].toInt(), timeTab[0].toInt(), timeTab[1].toInt())
  }

  private fun convertToYear(date: List<String>, size: Int = 3): Int {
    return if (date.size == size) {
      date[size - 1].toInt()
    } else {
      if (currentDate.monthOfYear > DateItems.repackMonth(date[size - 2])) {
        currentDate.year + 1
      } else {
        currentDate.year
      }
    }
  }

  companion object {
    private val FULL_PL_DATES_DOT_SEPARATE_REGEX: String = ".*((\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d)) (((([1-9]|([0-2]\\d)|3[01])).((([sS]tycz(e[nń]|nia))|([mM]ar(zec|ca))|([mM]aj(a|))|([lL]ip(iec|ca))|([sS]ierp(ie[nń]|nia))|([pP]a[zź]dziernik(a|))|([gG]rud(zie[nń]|nia)))|([13578]|10|12)))|((([1-9]|([0-2]\\d)|30)).((([kK]wie(cie[nń]|tnia))|([cC]zerw(iec|ca))|([wW]rze(sie[nń]|[sś]nia))|([lL]istopad(a|)))|([469]|11)))|((([1-9]|([0-2][0-8])))).((([lL]ut(y|ego)))|2))((.((20\\d\\d)))|)).*"
    private val FULL_PL_DATES_SLASH_SEPARATE_REGEX: String = ".*((\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d)) (((([1-9]|([0-2]\\d)|3[01]))/((([sS]tycz(e[nń]|nia))|([mM]ar(zec|ca))|([mM]aj(a|))|([lL]ip(iec|ca))|([sS]ierp(ie[nń]|nia))|([pP]a[zź]dziernik(a|))|([gG]rud(zie[nń]|nia)))|([13578]|10|12)))|((([1-9]|([0-2]\\d)|30))/((([kK]wie(cie[nń]|tnia))|([cC]zerw(iec|ca))|([wW]rze(sie[nń]|[sś]nia))|([lL]istopad(a|)))|([469]|11)))|((([1-9]|([0-2][0-8]))))/((([lL]ut(y|ego)))|2))((/((20\\d\\d)))|)).*"
    private val FULL_PL_DATES_DASH_SEPARATE_REGEX: String = ".*((\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d)) (((([1-9]|([0-2]\\d)|3[01]))-((([sS]tycz(e[nń]|nia))|([mM]ar(zec|ca))|([mM]aj(a|))|([lL]ip(iec|ca))|([sS]ierp(ie[nń]|nia))|([pP]a[zź]dziernik(a|))|([gG]rud(zie[nń]|nia)))|([13578]|10|12)))|((([1-9]|([0-2]\\d)|30))-((([kK]wie(cie[nń]|tnia))|([cC]zerw(iec|ca))|([wW]rze(sie[nń]|[sś]nia))|([lL]istopad(a|)))|([469]|11)))|((([1-9]|([0-2][0-8]))))-((([lL]ut(y|ego)))|2))((-((20\\d\\d)))|)).*"
    private val FULL_PL_DATES_WHITESPACE_SEPARATE_REGEX: String = ".*((\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d)) (((([1-9]|([0-2]\\d)|3[01])) ((([sS]tycz(e[nń]|nia))|([mM]ar(zec|ca))|([mM]aj(a|))|([lL]ip(iec|ca))|([sS]ierp(ie[nń]|nia))|([pP]a[zź]dziernik(a|))|([gG]rud(zie[nń]|nia)))|([13578]|10|12)))|((([1-9]|([0-2]\\d)|30)) ((([kK]wie(cie[nń]|tnia))|([cC]zerw(iec|ca))|([wW]rze(sie[nń]|[sś]nia))|([lL]istopad(a|)))|([469]|11)))|((([1-9]|([0-2][0-8])))) ((([lL]ut(y|ego)))|2))(( ((20\\d\\d)))|)).*"

    private val DATES_AS_WORDS_REGEX: String = ".*((\\d|([0-1]\\d)|(2[0-4])):(\\d|([0-5]\\d)) (([dD]zi[sś](|iaj))|([jJ]utro)|([Pp]ojutrze)|([zZ]a tydzie[ńn])|([zZ]a miesi[ąa]c))).*"
  }
}