package com.sakethh.arara.api

import com.sakethh.arara.Constants
import com.sakethh.arara.unreleased.UnreleasedListResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class UnreleasedAPIThing {
    private lateinit var apiData: UnreleasedAPI

    init {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiData = retrofitBuilder.create(UnreleasedAPI::class.java)
    }

    suspend fun getSongsData(): UnreleasedListResponse {
        return apiData.getSongsData()
    }

    interface UnreleasedAPI {
        @GET(Constants.UNRELEASED)
        suspend fun getSongsData(): UnreleasedListResponse
    }
}