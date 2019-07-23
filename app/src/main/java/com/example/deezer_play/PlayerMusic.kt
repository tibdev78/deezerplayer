package com.example.deezer_play

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import com.example.deezer_play.managers.TrackManager
import com.example.deezer_play.tracks.TracksData
import kotlinx.android.synthetic.main.track_fragment.view.*
import android.support.v7.widget.RecyclerView


@Suppress("DEPRECATION")
class PlayerMusic : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()


    lateinit var  title : RecyclerView

    fun setTrackMediaPlayer(context: Context) {
        try {
            mediaPlayer = MediaPlayer.create(context, parseStringToUri(TrackManager.newInstance(context).getCurrentTrack().preview))
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun parseStringToUri(url: String): Uri {
        val uri: Uri = Uri.parse(url)
        return uri
    }

    @SuppressLint("WrongConstant")
    fun prepareMediaPlayer(context: Context, view: View, albumsImage: String) {
        val image_play = ContextCompat.getDrawable(context, R.drawable.ic_play)
        val image_pause = ContextCompat.getDrawable(context, R.drawable.ic_pause)


        mediaPlayer.setOnPreparedListener {
            view.btPlay.setOnClickListener {
                if (!mediaPlayer.isPlaying) {
                    view.btPlay.setImageDrawable(image_pause)
                    mediaPlayer.start()
                    initInitializeSeekBar(view, context)
                    PlayerNotification().lunchNotification(view,context, albumsImage)
                }
                else {
                    view.btPlay.setImageDrawable(image_play)
                    mediaPlayer.pause()
                }
            }


            view.btNext.setOnClickListener {
                nextTrack(TrackManager.newInstance(context).getPosition(), TrackManager.newInstance(context).getCurrentTrackList(), context)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                setTrackMediaPlayer(context)
                view.btPlay.setImageDrawable(image_play)
                view.track_name.text = TrackManager.newInstance(context).getCurrentTrack().title

            }

            view.btPrevious.setOnClickListener {
                previousTrack(TrackManager.newInstance(context).getPosition(), TrackManager.newInstance(context).getCurrentTrackList(), context)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                setTrackMediaPlayer(context)
                view.btPlay.setImageDrawable(image_play)
                view.track_name.text = TrackManager.newInstance(context).getCurrentTrack().title
            }
        }


        mediaPlayer.setOnCompletionListener {MP ->
            handler.removeCallbacksAndMessages(null)
            handler.removeCallbacks(runnable)
            view.sbProgress.setProgress(0, true)
            view.currentTiming.text = context.getString(R.string.min_value_music)
            view.btPlay.setImageDrawable(image_play)
        }
    }

    fun initInitializeSeekBar(view: View, context: Context) {
        val seekBar: SeekBar = view.sbProgress

        seekBar.max = mediaPlayer.seconds

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentSeconds
            view.currentTiming.text = context.getString(R.string.duration_format, mediaPlayer.currentSeconds)

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun progressSeekBar(seekBar: SeekBar) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            handler.removeCallbacks(runnable)
        }
    }

    fun nextTrack(position: Int, tracksData: List<TracksData>, context: Context) {
        if (position + 1 < tracksData.size) {
            TrackManager.newInstance(context).setCurrentTrack(tracksData.get(position + 1))
            TrackManager.newInstance(context).setPosition(position + 1)
        } else {
            TrackManager.newInstance(context).setCurrentTrack(tracksData.get(0))
            TrackManager.newInstance(context).setPosition(0)
        }
    }

    fun previousTrack(position: Int, tracksData: List<TracksData>, context: Context) {
        if (position == 0) {
            TrackManager.newInstance(context).setCurrentTrack(tracksData.get(tracksData.size - 1))
            TrackManager.newInstance(context).setPosition(tracksData.size - 1)
        } else {
            TrackManager.newInstance(context).setCurrentTrack(tracksData.get(position - 1))
            TrackManager.newInstance(context).setPosition(position - 1)
        }
    }

    // Extension property to get media player current position in seconds
    val MediaPlayer.seconds: Int
        get() {
            return this.duration / 1000
        }
    // Extension function to show toast message quickly
    val MediaPlayer.currentSeconds:Int
        get() {
            return this.currentPosition/1000
        }
}