package com.sakethh.arara.unreleased

import com.sakethh.arara.Constants
import com.sakethh.arara.api.UnreleasedAPIThing
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class UnreleasedRepo(private val unreleasedAPIThing: UnreleasedAPIThing= UnreleasedAPIThing()) {
    fun getSongsData(): UnreleasedListResponse{
        return unreleasedAPIThing.getSongsData().execute().body()!!
    }

}

