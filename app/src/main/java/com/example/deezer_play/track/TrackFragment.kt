package com.example.deezer_play.track

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.R
import com.example.deezer_play.tracks.TracksActivity
import com.example.deezer_play.tracks.TracksData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tracks.view.*
import kotlinx.android.synthetic.main.item_tracks.*
import kotlinx.android.synthetic.main.track_fragment.*
import kotlinx.android.synthetic.main.track_fragment.track_name
import java.io.Serializable

class TrackFragment : Fragment() {

    companion object {
        const val ARGS_ALBUM_COVER = "albumCover"
        const val ARGS_ALBUM_TITLE = "albumTitle"
        const val ARGS_TRACKS_DATA = "tracksData"

        fun newInstance(albumCover: String, albumTitle: String, trackData: TracksData): TrackFragment {
            val fragment = TrackFragment()
            val bundle = Bundle().apply {
                putString(ARGS_ALBUM_COVER, albumCover)
                putString(ARGS_ALBUM_TITLE, albumTitle)
                putSerializable(ARGS_TRACKS_DATA, listOf(trackData) as Serializable)
            }

            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(false)
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val albumTitle = arguments?.getString("albumTitle")
        val cover = arguments?.getString("albumCover")
        @Suppress("UNCHECKED_CAST")
        val listTracksData = arguments?.getSerializable("tracksData") as List<TracksData>
        val tracksData = listTracksData.get(0)

        tracks_album.text = albumTitle
        track_name.text = tracksData.title

        Glide.with(this)
            .load(cover)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(track_cover)

        closeFragment()
    }

    private fun closeFragment() {
        btReduce.setOnClickListener {
            fragmentManager?.popBackStack()
            //show actionBar
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }

}