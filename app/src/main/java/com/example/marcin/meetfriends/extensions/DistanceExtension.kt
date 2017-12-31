package com.example.marcin.meetfriends.extensions

import android.content.Context
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.nearby_place.Distance
import java.text.DecimalFormat

/**
 * Created by marci on 2017-12-31.
 */
fun Distance.transformDistance(context: Context): String {
  val distanceInMeters = this.value
  if (distanceInMeters >= 1000) {
    return context.getString(R.string.kilometers, DecimalFormat(".##").format(distanceInMeters.toDouble() / 1000)
    )
  }
  return context.getString(R.string.meters, distanceInMeters.toString())
}