package com.example.deezer_play.tracks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.deezer_play.R
import com.example.deezer_play.buisiness.api.DeezerProvider
import kotlinx.android.synthetic.main.activity_tracks.*

class TracksActivity: AppCompatActivity(){

    private lateinit var tracksRecyclerView: RecyclerView
    private var tracksAdapter: TracksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracks)

        tracksRecyclerView = findViewById(R.id.trackslist_recycleview)

        displayedTracks()
    }

    private fun displayedTracks() {
        val idAlbum: String = intent.getStringExtra("idAlbum")
        DeezerProvider.getTracks(idAlbum, object: DeezerProvider.Listener<List<TracksData>> {
            override fun onSuccess(data: List<TracksData>) {
               tracksAdapter = TracksAdapter()
                tracksAdapter?.setData(data)
                tracksRecyclerView.adapter = tracksAdapter
                tracksRecyclerView.layoutManager = LinearLayoutManager(this@TracksActivity)
            }
            override fun onError(t: Throwable) {
                throw (t)
            }

        })
    }
}