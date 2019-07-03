package com.example.deezer_play.tracks

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.deezer_play.R
import com.example.deezer_play.buisiness.api.DeezerProvider
import kotlinx.android.synthetic.main.activity_tracks.*

class TracksActivity: AppCompatActivity() {

    private lateinit var tracksRecyclerView: RecyclerView
    private var tracksAdapter: TracksAdapter? = null
    private var albumInformationData = mutableListOf<String>()
    private var actionbar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracks)

        tracksRecyclerView = findViewById(R.id.trackslist_recycleview)

        this.actionbar = supportActionBar
        this.actionbar!!.setDisplayHomeAsUpEnabled(true)

        displayedTracks()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayedTracks() {
        val idAlbum: String = intent.getStringExtra("idAlbum")
        val nameAlbum: String = intent.getStringExtra("nameAlbum")
        val cover: String = intent.getStringExtra("cover")

        tracksAdapter = TracksAdapter()

        this.albumInformationData.add(0, nameAlbum)
        this.albumInformationData.add(1, cover)

        this.setInformationAlbum(this.albumInformationData)

        DeezerProvider.getTracks(idAlbum, object: DeezerProvider.Listener<List<TracksData>> {
            override fun onSuccess(data: List<TracksData>) {
                tracksAdapter?.setData(data)
                tracksRecyclerView.adapter = tracksAdapter
                tracksRecyclerView.layoutManager = LinearLayoutManager(this@TracksActivity)
            }
            override fun onError(t: Throwable) {
                throw (t)
            }

        })
    }

    private fun setInformationAlbum(albumInformation: List<String>) {
        album_title.text = albumInformation[0]
        Glide.with(this@TracksActivity)
            .load(albumInformation[1])
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(albums_cover)
        tracksAdapter?.setAlbumInformation(albumInformation)
    }
}