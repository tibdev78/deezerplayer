package com.example.deezer_play

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.time.Duration

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

    fun setMediaplayer(preview: String, context: Context) {
        mediaPlayer = MediaPlayer()
        val audioAttributes = createAudioAttributes()
        mediaPlayer.setAudioAttributes(audioAttributes)
        val previewUri: Uri = Uri.parse(preview)
        try {
            mediaPlayer.setDataSource(context, previewUri)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun prepareMediaPlayer(button: ImageView, context: Context) {
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
                }
                else {
                    button.setImageDrawable(image_play)
                    mediaPlayer.pause()
                }
            }
        }

        mediaPlayer.setOnCompletionListener {
            button.setImageDrawable(image_play)
        }
    }

    fun initInitializeSeekBar(duration: TextView, currentTiming: TextView, seekBar: SeekBar) {
        seekBar.max = mediaPlayer.seconds

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentSeconds

            duration.text = "0:${mediaPlayer.currentSeconds}"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            //currentTiming.text = "$diff"

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