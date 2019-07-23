package com.example.deezer_play.tracks

import com.google.gson.annotations.SerializedName

data class TracksResponse(@SerializedName("data") val tracksList: List<TracksData>)

data class TracksData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("preview")
    var preview: String,
    @SerializedName("artist")
    var artist: ArtistTracks)


data class ArtistTracks(
    @SerializedName("id")
    var idArtist: Int,
    @SerializedName("name")
    var name: String
)