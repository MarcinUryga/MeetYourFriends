package com.example.marcin.meetfriends.ui.planned_event_detail.event_questionnaire.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.FirebasePlace
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_venue.view.*

/**
 * Created by marci on 2017-12-31.
 */
class VenuesAdapter : RecyclerView.Adapter<VenuesViewHolder>() {

  private var venuesList = listOf<FirebasePlace>()
  private val publishSubject = PublishSubject.create<FirebasePlace>()
  private val publishSubjectClickedActionButton = PublishSubject.create<FirebasePlace>()

  fun addVenuesList(venuesList: List<FirebasePlace>) {
    this.venuesList = venuesList.distinct()
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VenuesViewHolder.create(parent)

  override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
    holder.bind(venuesList[position])
    holder.itemView.setOnClickListener {
      publishSubject.onNext(venuesList[position])
    }
    holder.itemView.voteVenueButton.setOnClickListener {
      publishSubjectClickedActionButton.onNext(venuesList[position])
    }
  }

  override fun getItemCount() = venuesList.size

  fun getClickEvent(): Observable<FirebasePlace> {
    return publishSubject
  }

  fun getClickedActionButtonEvent(): Observable<FirebasePlace> {
    return publishSubjectClickedActionButton
  }
}