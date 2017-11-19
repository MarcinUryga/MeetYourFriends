package com.example.marcin.meetfriends.utils

import android.content.Context

/**
 * Created by marci on 2017-11-19.
 */

fun Int.convertPixelsToDp(context: Context) = (this / (context.resources.displayMetrics.densityDpi / 160f)).toInt()