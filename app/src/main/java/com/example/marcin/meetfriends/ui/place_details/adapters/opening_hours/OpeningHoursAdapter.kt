package com.example.marcin.meetfriends.ui.place_details.adapters.opening_hours

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by marci on 2017-12-29.
 */
data class OpeningHoursAdapter(
    private val context: Context,
    private val openingHoursList: List<String>
) : BaseAdapter() {

  override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
    val openinhHoursTextView = TextView(context)
    openinhHoursTextView.text = openingHoursList[position]
    openinhHoursTextView.textSize = 12f
    openinhHoursTextView.setTextColor(Color.BLACK)
    return openinhHoursTextView
  }

  override fun getItem(position: Int) = openingHoursList[position]

  override fun getItemId(position: Int) = position.toLong()

  override fun getCount() = openingHoursList.size

}