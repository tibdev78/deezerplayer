package com.example.deezer_play

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class PlayerMusic {
    private lateinit var mediaPlayer: MediaPlayer
    private var playBackPosition = 0

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

    fun prepareMediaPlayer(button: ImageView) {
        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener {
            button.setOnClickListener {
                if (!mediaPlayer.isPlaying)
                    mediaPlayer.start()
                else
                    mediaPlayer.pause()
            }
        }
    }
}