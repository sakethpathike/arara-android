package com.sakethh.arara.api

import com.sakethh.arara.Constants
import com.sakethh.arara.unreleased.UnreleasedResponse
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

    suspend fun getSongsData(): List<UnreleasedResponse> {
        return apiData.getSongsData()
    }

    interface UnreleasedAPI {
        @GET(Constants.UNRELEASED)
        suspend fun getSongsData(): List<UnreleasedResponse>
    }
}