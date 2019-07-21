package com.example.deezer_play

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.Toast
import com.example.deezer_play.managers.TrackManager
import kotlinx.android.synthetic.main.notification_layout.view.*


class PlayerNotification {


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
     fun lunchNotification(view: View, context: Context){

         notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


         createNotificationChannel(
            "com.ebookfrenzy.notifydemo.news",
            "NotifyDemo News",
            "Example News Channel"
        )

        val channelID = "com.ebookfrenzy.notifydemo.news"
        val viewNotification = RemoteViews("com.example.deezer_play",R.layout.notification_layout)

        val notification = Notification.Builder(context,
            channelID)
            .setContent(viewNotification)
            .setContentTitle(TrackManager.newInstance(context).getCurrentTrack().title)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(Notification.PRIORITY_HIGH)
            .setChannelId(channelID)
            .build()
        val notificationID = 101

        notificationManager?.notify(notificationID, notification)

         view.n_back.setOnClickListener {
             Toast.makeText(context,"lolollllol", 1000 )
         }


     }

}