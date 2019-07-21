package com.example.deezer_play

import android.app.*
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.media.session.MediaSession
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.SeekBar
import com.example.deezer_play.managers.TrackManager
import com.example.deezer_play.tracks.TracksData
import kotlinx.android.synthetic.main.activity_tracks.view.*
import kotlinx.android.synthetic.main.track_fragment.view.*
import android.app.Activity
import android.preference.PreferenceManager
import android.content.SharedPreferences






@Suppress("DEPRECATION")
class PlayerMusic : Service() {

    private  var notificationManager: NotificationManager? = null


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()

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

    fun prepareMediaPlayer(context: Context, view: View) {
        val image_play = ContextCompat.getDrawable(context, R.drawable.ic_play)
        val image_pause = ContextCompat.getDrawable(context, R.drawable.ic_pause)

        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }

        mediaPlayer.setOnPreparedListener {
            view.btPlay.setOnClickListener {
                if (!mediaPlayer.isPlaying) {
                    view.btPlay.setImageDrawable(image_pause)
                    mediaPlayer.start()
                    initInitializeSeekBar(view, context)
                    setNotification(context, view)

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


    private fun createNotificationChannel(id: String, name: String,
                                          description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.vibrationPattern =
            longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }


    private fun setNotification(context: Context ,view: View){

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


     //   notificationManager =
       //     getSystemService(
      //          Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
            "com.ebookfrenzy.notifydemo.news",
            "NotifyDemo News",
            "Example News Channel"
        )

        val channelID = "com.ebookfrenzy.notifydemo.news"

        val mediaSession1 : MediaSession = MediaSession(context,"laMediaSession")

        val intent = Intent("snooze")
        val penndapp = PendingIntent.getBroadcast(context,12345,intent,PendingIntent.FLAG_UPDATE_CURRENT)


       /* val snoozeIntent = Intent(this, MyBroadcastReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)*/


        val viewNotification = RemoteViews("com.example.deezer_play",R.layout.notification_layout)

        val notification = Notification.Builder(context,
            channelID)
            .setContent(viewNotification)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(Notification.PRIORITY_HIGH)
            .setChannelId(channelID)
            .setStyle(Notification.MediaStyle()
                .setShowActionsInCompactView(0,1,2)
                .setMediaSession(mediaSession1.sessionToken))
            .addAction(R.drawable.ic_back, "back", penndapp)
            .addAction(R.drawable.ic_play, "pause", penndapp)
            .addAction(R.drawable.ic_next, "next", penndapp)
            .build()
        val notificationID = 101


        notificationManager?.notify(notificationID, notification)


        initactionnotif()


    }

    private fun initactionnotif() {



    }
}