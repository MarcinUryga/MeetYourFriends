package com.example.marcin.meetfriends.ui.chat

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.widget.TextView


/**
 * Created by marci on 2017-12-07.
 */
class MessageTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TextView(context, attrs, defStyle) {

  fun underlineDate(dateHandler: String) {
    val spannableDate = SpannableString(dateHandler)
    spannableDate.setSpan(UnderlineSpan(), 0, dateHandler.length, 0)
    val spannableStringBuilder = SpannableStringBuilder(text, 0, text.length)
    spannableStringBuilder.replace(text.indexOf(dateHandler), text.indexOf(dateHandler.last()) + 1, spannableDate)
    text = spannableStringBuilder
  }
}
