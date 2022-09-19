package com.sakethh.arara.api

import com.sakethh.arara.Constants
import com.sakethh.arara.MainActivity
import com.sakethh.arara.unreleased.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class UnreleasedAPIThing() {
    private lateinit var apiData: UnreleasedAPI
    private fun connectToNetwork(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(UnreleasedCache.okHttpClient)
            .build()
        apiData = retrofitBuilder.create(UnreleasedAPI::class.java)
    }
    init {
        try {
            connectToNetwork()
        }catch (_:Exception){
            connectToNetwork()
        }
    }
    suspend fun getSongsData(): List<UnreleasedResponse> {
        return apiData.getSongsData()
    }
    suspend fun getUnreleasedFooterImg():List<UnreleasedFooterImage>{
        return apiData.getUnreleasedFooterImg()
    }
    suspend fun getUnreleasedHeaderImg():List<UnreleasedArtwork>{
        return apiData.getUnreleasedHeaderImg()
    }
    interface UnreleasedAPI {
        @GET(Constants.UNRELEASED)
        suspend fun getSongsData(): List<UnreleasedResponse>
        @GET(Constants.UNRELEASED_FOOTER_IMG_URL)
        suspend fun getUnreleasedFooterImg():List<UnreleasedFooterImage>
        @GET(Constants.UNRELEASED_HEADER_IMG_URL)
        suspend fun getUnreleasedHeaderImg():List<UnreleasedArtwork>
    }
}