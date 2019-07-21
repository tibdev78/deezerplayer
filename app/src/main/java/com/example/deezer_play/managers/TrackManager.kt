package com.example.deezer_play.managers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.deezer_play.tracks.TracksData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class TrackManager(context: Context) {
    private var trackList: List<TracksData>? = null
    private val gson = Gson()
    private var settings: SharedPreferences? = context.getSharedPreferences("DeezerPlay", 0)

    companion object {
        fun newInstance(context: Context): TrackManager {
            return TrackManager(context)
        }
    }

    fun getCurrentTrack(): TracksData {
        val jsonTrack: String? = settings?.getString("track", "")
        val track: TracksData = gson.fromJson(jsonTrack, TracksData::class.java)
        return track
    }

    fun setCurrentTrack(tracksData: TracksData) {
        val jsonTrack: String = gson.toJson(tracksData)
        settings?.edit()?.putString("track", jsonTrack)?.apply()
    }

    fun getCurrentTrackList(): List<TracksData> {
        val jsonTrack: String? = settings?.getString("currentTrackList", "")
        val trackType: Type = object : TypeToken<List<TracksData>>() {}.type
        val trackList: List<TracksData> = Gson().fromJson<List<TracksData>>(jsonTrack, trackType)

        return trackList
    }

    fun setCurrentTrackList(tracksData: List<TracksData>) {
        val trackListJson: String = gson.toJson(tracksData)
        settings?.edit()?.putString("currentTrackList", trackListJson)?.apply()
    }

    fun getPosition(): Int {
        return settings?.getInt("position", 0) as Int
    }

    fun setPosition(position: Int) {
        settings?.edit()?.putInt("position", position)?.apply()
    }
}