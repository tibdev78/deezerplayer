package com.example.deezer_play.albums

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.R
import com.example.deezer_play.tracks.TracksActivity
import java.io.Serializable

class AlbumsAdapter: RecyclerView.Adapter<AlbumsAdapter.AlbumsListViewHolder>() {

    private var albumsData: List<AlbumsData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_albums, parent, false)
        return AlbumsListViewHolder(view)
    }

    override fun getItemCount() = albumsData?.size ?: 0

    override fun onBindViewHolder(holder: AlbumsListViewHolder, position: Int) {
        val AlbumsData = albumsData!![position]
        holder.albumsName.text = AlbumsData.title

        Glide.with(holder.itemView)
            .load(AlbumsData.cover)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.coverImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, TracksActivity::class.java)
            intent.putExtra("idAlbum", AlbumsData.id.toString())
            holder.itemView.context.startActivity(intent)
        }

    }

    fun setData(data: List<AlbumsData>) {
        this.albumsData = data
        notifyDataSetChanged()
    }

    class AlbumsListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var coverImage: ImageView = itemView.findViewById(R.id.cover_image)
        val albumsName: TextView = itemView.findViewById(R.id.album_name)
    }
}