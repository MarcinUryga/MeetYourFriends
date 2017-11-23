package com.example.marcin.meetfriends.ui.common

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by marci on 2017-11-22.
 */
open class BaseViewHolder protected constructor(view: View) : RecyclerView.ViewHolder(view) {

  companion object {
    fun empty(parent: ViewGroup): BaseViewHolder {
      return BaseViewHolder(View(parent.context))
    }
  }

}