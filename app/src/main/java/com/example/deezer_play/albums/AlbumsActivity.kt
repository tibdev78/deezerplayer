package com.example.deezer_play.albums

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import com.example.deezer_play.R
import com.example.deezer_play.buisiness.api.DeezerProvider

class AlbumsActivity: AppCompatActivity(){

    private lateinit var albumsRecyclerView: RecyclerView
    private var albumsAdapter: AlbumsAdapter? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        albumsRecyclerView = findViewById(R.id.albumslist_recyclerview)
        displayedAlbums()
    }

    private fun displayedAlbums() {
        DeezerProvider.getAlbums(object: DeezerProvider.Listener<List<AlbumsData>> {
            override fun onSuccess(data: List<AlbumsData>) {
                //showData(data)
                albumsAdapter = AlbumsAdapter()
                albumsAdapter?.setData(data)
                albumsRecyclerView.adapter = albumsAdapter
                albumsRecyclerView.layoutManager = LinearLayoutManager(this@AlbumsActivity)
            }
            override fun onError(t: Throwable) {
                throw (t)
            }

        })
    }

}