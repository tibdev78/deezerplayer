package com.example.deezer_play.albums

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.R

class AlbumsAdapter(private val albums: List<AlbumsData>): RecyclerView.Adapter<AlbumsAdapter.AlbumsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_albums, parent, false)
        return AlbumsListViewHolder(view)
    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: AlbumsListViewHolder, position: Int) {
        val AlbumsData = albums[position]
        holder.albumsName.text = AlbumsData.title

        Glide.with(holder.itemView)
            .load(AlbumsData.cover)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.coverImage)
        //holder.coverImage = AlbumsData.cover
    }

    class AlbumsListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var coverImage: ImageView = itemView.findViewById(R.id.cover_image)
        val albumsName: TextView = itemView.findViewById(R.id.album_name)
    }
}