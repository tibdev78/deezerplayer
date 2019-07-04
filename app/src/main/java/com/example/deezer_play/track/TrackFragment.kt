package com.example.deezer_play.track

import android.app.ActionBar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.R
import com.example.deezer_play.tracks.TracksActivity
import kotlinx.android.synthetic.main.activity_tracks.view.*
import kotlinx.android.synthetic.main.track_fragment.*

class TrackFragment : Fragment() {

    companion object {
        const val ARGS_ALBUM_COVER = "albumCover"
        const val ARGS_ALBUM_TITLE = "albumTitle"

        fun newInstance(albumCover: String, albumTitle: String): TrackFragment {
            val fragment = TrackFragment()
            val bundle = Bundle().apply {
                putString(ARGS_ALBUM_COVER, albumCover)
                putString(ARGS_ALBUM_TITLE, albumTitle)
            }

            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val albumTitle = arguments?.getString("albumTitle")
        val cover = arguments?.getString("albumCover")

        tracks_album.text = albumTitle

        Glide.with(this)
            .load(cover)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(track_cover)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}