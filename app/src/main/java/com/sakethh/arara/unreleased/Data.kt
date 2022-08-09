package com.sakethh.arara.unreleased

import com.sakethh.arara.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Data {
    val retrofit: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}