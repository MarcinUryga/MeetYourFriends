package com.example.marcin.meetfriends.utils

/**
 * Created by marci on 2017-12-28.
 */
object LatLngConverter {

  const val EARTH_RADIUS = 6371

  fun coutnDistanceFromLatLng(firstLat: Double, firstLng: Double, secondLat: Double, secondLng: Double): Double {
    val latDistance = Math.toRadians(secondLat - firstLat)
    val lonDistance = Math.toRadians(secondLng - firstLng)
    val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(firstLat)) * Math.cos(Math.toRadians(secondLat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2))
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    var distance = EARTH_RADIUS.toDouble() * c * 1000.0 // convert to meters
    distance = Math.pow(distance, 2.0)
    return Math.sqrt(distance)
  }

}