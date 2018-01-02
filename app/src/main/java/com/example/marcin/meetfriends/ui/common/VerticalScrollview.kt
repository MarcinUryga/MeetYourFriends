package com.example.marcin.meetfriends.ui.common

/**
 * Created by marci on 2017-12-14.
 */
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import timber.log.Timber

class VerticalScrollview : ScrollView {

  constructor(context: Context) : super(context) {}

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    val action = ev.action
    when (action) {
      MotionEvent.ACTION_DOWN -> {
        Timber.d("onInterceptTouchEvent: DOWN super false")
        super.onTouchEvent(ev)
      }

      MotionEvent.ACTION_MOVE -> return false // redirect MotionEvents to ourself

      MotionEvent.ACTION_CANCEL -> {
        Timber.d("onInterceptTouchEvent: CANCEL super false")
        super.onTouchEvent(ev)
      }

      MotionEvent.ACTION_UP -> {
        Timber.d("onInterceptTouchEvent: UP super false")
        return false
      }

      else -> Timber.d("onInterceptTouchEvent: " + action)
    }

    return false
  }

  override fun onTouchEvent(ev: MotionEvent): Boolean {
    super.onTouchEvent(ev)
    Timber.d("onTouchEvent. action: " + ev.action)
    return true
  }
}