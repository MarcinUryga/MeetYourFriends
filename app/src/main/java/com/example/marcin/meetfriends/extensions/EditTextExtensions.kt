package com.example.marcin.meetfriends.extensions

import android.widget.EditText
import android.widget.LinearLayout
import com.example.marcin.meetfriends.utils.convertPixelsToDp

/**
 * Created by marci on 2017-11-19.
 */

fun EditText.setMargins(left: Int, right: Int, top: Int, bottom: Int): EditText {
  val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
  layoutParams.setMargins(
      left.convertPixelsToDp(this.context),
      top.convertPixelsToDp(this.context),
      right.convertPixelsToDp(this.context),
      bottom.convertPixelsToDp(this.context)
  )
  this.layoutParams = layoutParams
  return this
}

fun EditText.setEditTextHint(text: String): EditText {
  this.hint = text
  return this
}