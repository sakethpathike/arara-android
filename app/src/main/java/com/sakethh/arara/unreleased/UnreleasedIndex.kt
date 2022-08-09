package com.sakethh.arara.unreleased

import retrofit2.Call
import retrofit2.http.GET

data class UnreleasedIndex(
    var songName: String,
    var specificArtwork: String
)

interface ApiInterface{
    @GET("/unreleased")
    fun getData():Call<List<UnreleasedIndex>>
}
