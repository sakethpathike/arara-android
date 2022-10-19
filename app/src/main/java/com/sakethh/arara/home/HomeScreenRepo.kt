package com.sakethh.arara.home

import com.sakethh.arara.api.FetchingAPIDATA
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData

class HomeScreenRepo(private val fetchingAPIDATA: FetchingAPIDATA= FetchingAPIDATA()) {
    suspend fun getFanartsTopAllTime(): List<SubRedditData> {
        return fetchingAPIDATA.getFanartsTopAllTime()
    }
    suspend fun getNewsTopAllTime(): List<SubRedditData> {
        return fetchingAPIDATA.getNewsTopAllTime()
    }
    suspend fun getImagesTopAllTime(): List<SubRedditData> {
        return fetchingAPIDATA.getImagesTopAllTime()
    }

}