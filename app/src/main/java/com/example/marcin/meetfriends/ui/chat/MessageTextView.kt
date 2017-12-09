package com.example.marcin.meetfriends.ui.chat

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.widget.TextView
import java.util.regex.Pattern

/**
 * Created by marci on 2017-12-07.
 */
class MessageTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TextView(context, attrs, defStyle) {

  private val FULL_ENG_DATES_REGEX: String = "^(\\d|(\\d\\d))( |.|/|-)(([jJ]an|[fF]eb|[mM]ar|[aA]pr|[mM]ay|[jJ]un|[jJ]ul|[aA]ug|[sS]ep|[oO]ct|[nN]ov|[dD]ec)|([1-9]|(1[0-2])))( |.|/|-)((20\\d\\d))"
  private val FULL_PL_DATES_REGEX: String = ".*((\\d|(\\d\\d))( |.|/|-)(([sS]tycze[nń]|[lL]uty|[mM]arzec|[kK]wiecie[nń]|[mM]aj|[cC]erwiec|[lL]ipiec|[sS]ierpie[nń]|[wW]rzesie[nń]|[pP]a[zź]dziernik|[lL]istopad|[gG]rudzie[nń])|([1-9]|(1[0-2])))( |.|/|-)((20\\d\\d)))"

  private val DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"

  init {
DATE_PATTERN.toRegex()
    FULL_PL_DATES_REGEX.toRegex()
    this.text = Html.fromHtml("<u>$text</u>")
  }

  fun containsDate(): Boolean {

    val pattern = Pattern.compile(FULL_ENG_DATES_REGEX)
    val matcher = pattern.matcher(text)
    if (matcher.find()) {
      text = Html.fromHtml("<u>${matcher.group()}</u>")
      return true
    }
    return false

    /*
    val string: String = "dasdasdasdasd"
    string.matches(FULL_ENG_DATES_REGEX.toRegex())
    *//*if (text.toString().matches("[a-zA-Z]{3}")) {
      text = Html.fromHtml("<u>$text</u>")
      return true
    }*//*
    return false*/
  }
}