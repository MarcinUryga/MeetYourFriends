package com.example.marcin.meetfriends.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by marci on 2017-12-30.
 */
object KeyboardUtils {

  fun hide(view: View?) {
    view?.let {
      (it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
          .hideSoftInputFromWindow(view.windowToken, 0)
    }
  }
}