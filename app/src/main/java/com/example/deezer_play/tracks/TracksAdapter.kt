package com.example.deezer_play.tracks

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.deezer_play.R

class TracksAdapter: RecyclerView.Adapter<TracksAdapter.TracksListViewHolder>() {

    private var tracksData: List<TracksData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tracks, parent, false)
        return TracksListViewHolder(view)
    }

    override fun getItemCount() = tracksData?.size ?: 0

    override fun onBindViewHolder(holder: TracksListViewHolder, position: Int) {
        val tracksData: TracksData = tracksData!![position]
        holder.trackName.text = tracksData.title

    }

    fun setData(data: List<TracksData>) {
        this.tracksData = data
        notifyDataSetChanged()
    }

    class TracksListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val trackName: TextView = itemView.findViewById(R.id.track_name)
    }
}