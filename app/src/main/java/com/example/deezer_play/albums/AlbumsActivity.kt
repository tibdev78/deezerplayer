package com.example.deezer_play.albums

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.deezer_play.R
import com.example.deezer_play.buisiness.api.DeezerProvider
import kotlinx.android.synthetic.main.activity_albums.*

class AlbumsActivity: AppCompatActivity(), AlbumsView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        displayedAlbums()
    }

    private fun displayedAlbums() {
        DeezerProvider.getAlbums(object: DeezerProvider.Listener<List<AlbumsData>> {
            override fun onSuccess(data: List<AlbumsData>) {
                showData(data)
            }
            override fun onError(t: Throwable) {
                throw (t)
            }

        })
    }

    private fun showData(albums: List<AlbumsData>) {
        albumslist_recyclerview.apply {
            layoutManager = LinearLayoutManager(this@AlbumsActivity)
            adapter = AlbumsAdapter(albums)
        }
    }

}