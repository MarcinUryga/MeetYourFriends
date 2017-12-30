package com.example.marcin.meetfriends.ui.place_details.adapters.review_adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.nearby_place.Review

/**
 * Created by marci on 2017-12-29.
 */
class ReviewAdapter(
    private val reviewList: List<Review>
) : RecyclerView.Adapter<ReviewViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder.create(parent)

  override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) = holder.bind(reviewList[position])

  override fun getItemCount() = reviewList.size
}