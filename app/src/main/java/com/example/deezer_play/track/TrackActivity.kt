package com.example.deezer_play.track

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class TrackActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val coverAlbum: String = intent.getStringExtra("coverAlbum")
        Log.d("ffff", "${coverAlbum}")

    }
}