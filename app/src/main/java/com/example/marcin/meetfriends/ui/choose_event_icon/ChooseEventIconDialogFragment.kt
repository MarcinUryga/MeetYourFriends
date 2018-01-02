package com.example.marcin.meetfriends.ui.choose_event_icon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView.OnItemClickListener
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.mvp.BaseFragmentDialog
import com.example.marcin.meetfriends.ui.choose_event_icon.adapter.EventIconsAdapter
import com.example.marcin.meetfriends.ui.choose_event_icon.viewmodel.EventIconEnum
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_choose_event_icon.*


/**
 * Created by marci on 2017-12-15.
 */
class ChooseEventIconDialogFragment : BaseFragmentDialog<ChooseEventIconContract.Presenter>(), ChooseEventIconContract.View {

  lateinit var listener: OnIconSelectedListener

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
    try {
      listener = context as OnIconSelectedListener
    } catch (e: ClassCastException) {
      throw ClassCastException(context.toString() + " must implement OnIconSelectedListener")
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
    return inflater.inflate(R.layout.fragment_choose_event_icon, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    eventIconsGridView.adapter = EventIconsAdapter(context)
    eventIconsGridView.onItemClickListener = OnItemClickListener { adapterView, _, position, _ ->
      listener.onIconSelected(adapterView.adapter.getItem(position) as EventIconEnum)
      dismiss()
    }
//    eventIconsGridView.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->  }
  }
}