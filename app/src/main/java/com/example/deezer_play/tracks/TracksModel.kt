package com.example.deezer_play.tracks

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

data class TracksResponse(@SerializedName("data") val tracksList: List<TracksData>)

data class TracksData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("artist")
    var artist: ArtistTracks)


data class ArtistTracks(
    @SerializedName("id")
    var idArtist: Int,
    @SerializedName("name")
    var name: String
)