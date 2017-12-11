package com.example.marcin.meetfriends.ui.chat.viewmodel

import org.joda.time.DateTime

/**
 * Created by marci on 2017-12-11.
 */
enum class DateItems(val polishName: Regex) {
  //months
  JANUARY("([sS]tycz(e[nń]|nia))".toRegex()),
  FEBRUARY("([lL]ut(y|ego))".toRegex()),
  MARCH("([mM]ar(zec|ca))".toRegex()),
  APRIL("([kK]wie(cie[nń]|tnia))".toRegex()),
  MAY("([mM]aj(a|))".toRegex()),
  JUNE("([cC]zerw(iec|ca))".toRegex()),
  JULY("([lL]ip(iec|ca))".toRegex()),
  AUGUST("([sS]ierp(ie[nń]|nia))".toRegex()),
  SEPTEMBER("([wW]rze(sie[nń]|[sś]nia))".toRegex()),
  OCTOBER("([pP]a[zź]dziernik(a|))".toRegex()),
  NOVEMBER("([lL]istopad(a|))".toRegex()),
  DECEMBER("([gG]rud(zie[nń]|nia))".toRegex()),
  //OTHERS
  TODAY("([dD]zi[sś](|iaj))".toRegex()),
  TOMMOROW("([jJ]utro)".toRegex()),
  DAY_AFTER_TOMMOROW("([Pp]ojutrze)".toRegex()),
  NEXT_WEEK("([zZ]a tydzie[nń])".toRegex()),
  NEXT_MONTH("([zZ]a miesi[ąa]c)".toRegex());


  companion object {
    fun repackMonth(month: String): Int {
      return when {
        DateItems.JANUARY.polishName.matches(month) -> 1
        DateItems.FEBRUARY.polishName.matches(month) -> 2
        DateItems.MARCH.polishName.matches(month) -> 3
        DateItems.APRIL.polishName.matches(month) -> 4
        DateItems.MAY.polishName.matches(month) -> 5
        DateItems.JUNE.polishName.matches(month) -> 6
        DateItems.JULY.polishName.matches(month) -> 7
        DateItems.AUGUST.polishName.matches(month) -> 8
        DateItems.SEPTEMBER.polishName.matches(month) -> 9
        DateItems.OCTOBER.polishName.matches(month) -> 10
        DateItems.NOVEMBER.polishName.matches(month) -> 11
        DateItems.DECEMBER.polishName.matches(month) -> 12
        else -> month.toInt()
      }
    }

    fun createDateFromOthersWords(word: String, timeTab: List<String>): DateTime {
      val currentDate = DateTime()
      val newDate = when {
        DateItems.TODAY.polishName.matches(word) -> DateTime(currentDate.year, currentDate.monthOfYear, currentDate.dayOfMonth, 0, 0)
        DateItems.TOMMOROW.polishName.matches(word) -> currentDate.plusDays(1)
        DateItems.DAY_AFTER_TOMMOROW.polishName.matches(word) -> currentDate.plusDays(2)
        DateItems.NEXT_WEEK.polishName.matches(word) -> currentDate.plusWeeks(1)
        DateItems.NEXT_MONTH.polishName.matches(word) -> currentDate.plusMonths(1)
        else -> currentDate
      }
      return newDate.withTime(timeTab[0].toInt(), timeTab[1].toInt(), 0, 0)
    }
  }
}