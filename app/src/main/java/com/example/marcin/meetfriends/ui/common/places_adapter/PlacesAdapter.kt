package com.example.marcin.meetfriends.ui.common.places_adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Place
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_place.view.*

/**
 * Created by marci on 2017-12-27.
 */
class PlacesAdapter(
    private var ifActionButtonIsChangeable: Boolean = true
) : RecyclerView.Adapter<PlacesViewHolder>() {

  private val publishSubject = PublishSubject.create<Place>()
  private val publishSubjectClickedActionButton = PublishSubject.create<Place>()

  private var placesList = mutableListOf<Place>()

  fun addPlace(place: Place) {
    placesList.add(place)
    placesList.sortBy { it.distance?.value }
    notifyDataSetChanged()
  }

  fun removePlace(place: Place) {
    placesList.remove(place)
    notifyDataSetChanged()
    /*var removedIndex = -1
    placesList.forEachIndexed { index, venue ->
      if (place == venue) {
        removedIndex = index
      }
    }
    placesList.removeAt(removedIndex)
    notifyItemRemoved(removedIndex)*/
  }

  fun clearPlaceList() {
    placesList.clear()
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlacesViewHolder.create(parent)

  override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
    holder.bind(placesList[position])
    holder.itemView.setOnClickListener {
      publishSubject.onNext(placesList[position])
    }
    holder.itemView.actionButton.setOnClickListener {
      publishSubjectClickedActionButton.onNext(placesList[position])
      if (ifActionButtonIsChangeable) {
        placesList[position].let { it.isAdded = !it.isAdded }
        notifyDataSetChanged()
      }
    }
  }

  override fun getItemCount() = placesList.size

  fun getClickEvent(): Observable<Place> {
    return publishSubject
  }

  fun getClickedActionButtonEvent(): Observable<Place> {
    return publishSubjectClickedActionButton
  }
}