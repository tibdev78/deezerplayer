package com.example.deezer_play

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.track_fragment.view.*
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class PlayerMusic {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

    private fun createAudioAttributes(): AudioAttributes {
        val builder = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        return builder.build()
    }

    fun setMediaplayer(preview: String) {
        mediaPlayer = MediaPlayer()
        val audioAttributes = createAudioAttributes()
        mediaPlayer.setAudioAttributes(audioAttributes)
        try {
            mediaPlayer.setDataSource(preview)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun prepareMediaPlayer(button: ImageView, context: Context, view: View) {
        val image_play = ContextCompat.getDrawable(context, R.drawable.ic_play)
        val image_pause = ContextCompat.getDrawable(context, R.drawable.ic_pause)

        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener {
            button.setOnClickListener {
                if (!mediaPlayer.isPlaying) {
                    button.setImageDrawable(image_pause)
                    mediaPlayer.start()
                    initInitializeSeekBar(view, context)
                }
                else {
                    button.setImageDrawable(image_play)
                    mediaPlayer.pause()
                }
            }
        }

        mediaPlayer.setOnCompletionListener {MP ->
            handler.removeCallbacksAndMessages(null)
            handler.removeCallbacks(runnable)
            view.sbProgress.setProgress(0, true)
            view.currentTiming.text = context.getString(R.string.min_value_music)
            button.setImageDrawable(image_play)
        }
    }

    fun initInitializeSeekBar(view: View, context: Context) {
        val seekBar: SeekBar = view.sbProgress

        seekBar.max = mediaPlayer.seconds

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentSeconds
            //duration.text = "0:${mediaPlayer.currentSeconds}"
            //val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
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

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

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