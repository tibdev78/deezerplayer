package com.example.deezer_play.track

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.PlayerMusic
import com.example.deezer_play.R
import com.example.deezer_play.managers.TrackManager
import com.example.deezer_play.tracks.TracksData
import kotlinx.android.synthetic.main.track_fragment.*
import kotlinx.android.synthetic.main.track_fragment.track_name
import kotlinx.android.synthetic.main.track_fragment.view.*
import java.io.Serializable

class TrackFragment : Fragment() {

    private var playerMusic: PlayerMusic = PlayerMusic()
    private lateinit var trackManager: TrackManager

    companion object {
        const val ARGS_ALBUM_INFO = "albumInfo"
        const val ARGS_TRACK = "trackData"
        const val ARGS_LIST_TRACKS = "tracksList"
        const val ARGS_POSITION_TRACK = "positionTrack"

        fun newInstance(albumInformation: List<String>, trackData: TracksData, tracksListData: List<TracksData>, position: Int): TrackFragment {
            val fragment = TrackFragment()
            val bundle = Bundle().apply {
                putSerializable(ARGS_ALBUM_INFO, albumInformation as Serializable)
                putSerializable(ARGS_TRACK, listOf(trackData) as Serializable)
                putSerializable(ARGS_LIST_TRACKS, tracksListData as Serializable)
                putInt(ARGS_POSITION_TRACK, position)
            }
            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val albumInformation = arguments?.getSerializable("albumInfo") as List<String>
        val trackData = arguments?.getSerializable("trackData") as List<TracksData>
        val tracksList = arguments?.getSerializable("tracksList") as List<TracksData>
        val positionTrack = arguments?.getInt("positionTrack") as Int

        tracks_album.text = albumInformation.get(0)
        track_name.text = trackData.get(0).title

        Glide.with(this)
            .load(albumInformation.get(1))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(track_cover)

        closeFragment()

        //init default value track manager
        trackManager = TrackManager(view.context)
        trackManager.setCurrentTrackList(tracksList)
        trackManager.setCurrentTrack(trackData.get(0))
        trackManager.setPosition(positionTrack)

        //music process
        launchMusic(view)
    }

    private fun closeFragment() {
        btReduce.setOnClickListener {
            fragmentManager?.popBackStack()
            //show actionBar
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }
    private fun launchMusic(view: View) {
        context?.also { ctx ->
            playerMusic.setTrackMediaPlayer(ctx)
            playerMusic.prepareMediaPlayer(ctx, view)
            playerMusic.progressSeekBar(sbProgress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playerMusic.stopMusic()
    }





}