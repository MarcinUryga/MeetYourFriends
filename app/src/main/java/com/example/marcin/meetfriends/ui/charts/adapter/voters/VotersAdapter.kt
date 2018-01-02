package com.example.marcin.meetfriends.ui.charts.adapter.voters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.marcin.meetfriends.models.Voter

/**
 * Created by marci on 2018-01-02.
 */
class VotersAdapter(
    private val votersList: List<Voter>
) : RecyclerView.Adapter<VotersViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VotersViewHolder.create(parent)

  override fun onBindViewHolder(holder: VotersViewHolder, position: Int) = holder.bind(votersList[position])

  override fun getItemCount() = votersList.size
}