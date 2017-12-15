package com.example.marcin.meetfriends.ui.choose_event_icon.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.marcin.meetfriends.ui.choose_event_icon.viewmodel.EventIconEnum


/**
 * Created by marci on 2017-12-15.
 */
class EventIconsAdapter(private val context: Context) : BaseAdapter() {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val imageView: ImageView
    if (convertView == null) {
      imageView = ImageView(context)
      imageView.layoutParams = AbsListView.LayoutParams(85, 85)
      imageView.scaleType = ImageView.ScaleType.CENTER_CROP
      imageView.setPadding(8, 8, 8, 8)
    } else {
      imageView = convertView as ImageView
    }

    imageView.setImageResource(eventIcons[position].resourceId)
    return imageView
  }

  override fun getItem(position: Int): EventIconEnum {
    return eventIcons[position]
  }

  override fun getItemId(p0: Int): Long {
    return 0
  }

  override fun getCount() = eventIcons.size

  private val eventIcons = listOf(
      EventIconEnum.BEER,
      EventIconEnum.BILLARD,
      EventIconEnum.BOWLING,
      EventIconEnum.LUNCH,
      EventIconEnum.DINNER,
      EventIconEnum.DARTS,
      EventIconEnum.TEA

  )
}