package com.example.marcin.meetfriends.ui.search_venues.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.ui.search_venues.viewmodel.Place
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * Created by marci on 2017-12-27.
 */
class VenuesAdapter(
    private val venues: List<Place>
) : RecyclerView.Adapter<VenuesViewHolder>() {

  private val publishSubject = PublishSubject.create<Place>()
  private val publishSubjectClickedActionButton = PublishSubject.create<Place>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VenuesViewHolder.create(parent)

  override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
    holder.bind(venues[position])
    holder.itemView.setOnClickListener {
      publishSubject.onNext(venues[position])
    }
    holder.itemView.actionButton.setOnClickListener {
      publishSubjectClickedActionButton.onNext(venues[position])
      venues[position].isAdded = !venues[position].isAdded
      notifyDataSetChanged()
    }
  }

  override fun getItemCount() = venues.size

  fun getClickEvent(): Observable<Place> {
    return publishSubject
  }

  fun getClickedActionButtonEvent(): Observable<Place> {
    return publishSubjectClickedActionButton
  }

}