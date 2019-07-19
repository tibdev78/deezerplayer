package com.example.deezer_play.track


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.PlayerMusic
import com.example.deezer_play.R
import com.example.deezer_play.tracks.TracksData
import kotlinx.android.synthetic.main.track_fragment.*
import kotlinx.android.synthetic.main.track_fragment.track_name
import java.io.Serializable

class TrackFragment : Fragment() {

    private var playerMusic: PlayerMusic = PlayerMusic()

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
        val playButon: ImageView = view.findViewById(R.id.btPlay)
        launchMusic(tracksData.preview, playButon, view)
    }

    private fun closeFragment() {
        btReduce.setOnClickListener {
            fragmentManager?.popBackStack()
            //show actionBar
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }
    private fun launchMusic(preview: String, button: ImageView, view: View) {
        context?.also { ctx ->
            playerMusic.setMediaplayer(preview)
            playerMusic.prepareMediaPlayer(button, ctx, view)
            playerMusic.progressSeekBar(sbProgress)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playerMusic.stopMusic()
    }





}