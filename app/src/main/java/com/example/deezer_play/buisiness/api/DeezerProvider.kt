package com.example.deezer_play.buisiness.api

import android.util.Log
import com.example.deezer_play.albums.AlbumsData
import com.example.deezer_play.albums.AlbumsResponse
import com.example.deezer_play.tracks.TracksData
import com.example.deezer_play.tracks.TracksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DeezerProvider {
    private val URL_API = "https://api.deezer.com"
    private var service: DeezerService

    init {
        service = Retrofit.Builder().baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeezerService::class.java)
    }

    fun getAlbums(listener: Listener<List<AlbumsData>>) {
        service.getAlbumsUser().enqueue(object: Callback<AlbumsResponse> {
            override fun onFailure(call: Call<AlbumsResponse>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(call: Call<AlbumsResponse>, response: Response<AlbumsResponse>) {
                val body = response.body()!!.albumsList
                listener.onSuccess(body)
            }

        })

    }

    fun getTracks(idAlbum: String, listener: Listener<List<TracksData>>) {
        service.getTracksAlbum(idAlbum).enqueue(object: Callback<TracksResponse> {
            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) {
                val  body = response.body()!!.tracksList
                listener.onSuccess(body)
            }

        })
    }

    interface Listener<T> {
        fun onSuccess(data: T)
        fun onError(t: Throwable)
    }
}
