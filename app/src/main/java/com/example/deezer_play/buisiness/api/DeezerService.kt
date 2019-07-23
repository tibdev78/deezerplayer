package com.example.deezer_play.buisiness.api

import com.example.deezer_play.albums.AlbumsResponse
import com.example.deezer_play.tracks.TracksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerService {
    @GET("/user/2892838484/albums")
    fun getAlbumsUser(): Call<AlbumsResponse>

    @GET("/album/{idAlbum}/tracks")
    fun getTracksAlbum(@Path("idAlbum") idAlbum: String): Call<TracksResponse>
}