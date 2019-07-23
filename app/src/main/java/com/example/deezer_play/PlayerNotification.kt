package com.example.deezer_play

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import com.example.deezer_play.managers.TrackManager
import com.example.deezer_play.tracks.TracksActivity


class PlayerNotification {

    companion object {
        fun newInstance(): PlayerMusic {
            return PlayerMusic()
        }
    }

    private  var notificationManager : NotificationManager? =null

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


     @SuppressLint("WrongConstant", "ShowToast")
     fun lunchNotification(view: View, context: Context, albumsImage: String){

         val intent = Intent(context, TracksActivity::class.java)
         val playTrack = Intent("play_track")
         val pendingPlayTrack: PendingIntent = PendingIntent.getBroadcast(context, 0, playTrack, PendingIntent.FLAG_UPDATE_CURRENT)
         val channelID = "com.ebookfrenzy.notifydemo.news"
         val viewNotification = RemoteViews("com.example.deezer_play",R.layout.notification_layout)
         val notificationID = 101


         notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


         createNotificationChannel(
            "com.ebookfrenzy.notifydemo.news",
            "NotifyDemo News",
            "Example News Channel"
        )

        val notification = Notification.Builder(context,
            channelID)
            .setContent(viewNotification)
            .setContentTitle(TrackManager.newInstance(context).getCurrentTrack().title)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(Notification.PRIORITY_HIGH)
            .setChannelId(channelID)
            .build()

         viewNotification.setOnClickPendingIntent(R.id.n_pause, pendingPlayTrack)

         viewNotification.setTextViewText(R.id.n_title, TrackManager.newInstance(context).getCurrentTrack().title)
         viewNotification.setImageViewResource(R.id.n_cover, R.drawable.bg_placeholder_cover)

        notificationManager?.notify(notificationID, notification)
     }
}