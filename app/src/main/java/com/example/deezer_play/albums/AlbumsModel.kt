package com.example.deezer_play.albums

import com.google.gson.annotations.SerializedName

data class AlbumsResponse(@SerializedName("data") val albumsList: List<AlbumsData>)

data class AlbumsData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("cover_small")
    var cover: String,
    @SerializedName("tracklist")
    var tracklist: String,
    @SerializedName("nb_tracks")
    var nbTracks: Int)