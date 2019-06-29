package com.example.deezer_play.buisiness.api

import android.util.Log
import com.example.deezer_play.albums.AlbumsData
import com.example.deezer_play.albums.AlbumsResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DeezerProvider {
    private val USER_ID = "2892838484"
    private val URL_API = "https://api.deezer.com"
    private var service: DeezerService
    private var albumsData: List<AlbumsData>? = null

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

    interface Listener<T> {
        fun onSuccess(data: T)
        fun onError(t: Throwable)
    }
}
