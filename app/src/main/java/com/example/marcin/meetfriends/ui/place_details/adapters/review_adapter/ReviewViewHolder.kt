package com.example.marcin.meetfriends.ui.place_details.adapters.review_adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marcin.meetfriends.R
import com.example.marcin.meetfriends.models.nearby_place.Review
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_place_review.view.*

/**
 * Created by marci on 2017-12-29.
 */
class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  companion object {

    fun create(parent: ViewGroup): ReviewViewHolder {
      return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_review, parent, false))
    }
  }

  fun bind(review: Review) {
    itemView.authorNameTextView.text = review.authorName
    itemView.opinionTextView.text = review.text
    itemView.ratingTextView.text = review.rating.toString()
    itemView.relativeTimeDescription.text = review.relativeTimeDescription
    Picasso.with(itemView.context).load(review.profilePhotoUrl).placeholder(R.drawable.placeholder).into(itemView.authorImageView)
  }
}