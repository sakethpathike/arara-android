package com.sakethh.arara.home.selectedChipStuff

import com.sakethh.arara.RealmDBObject
import com.sakethh.arara.api.FetchingAPIDATA
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.coroutineScope

class SelectedChipScreenAPIRepo(private val fetchingAPIDATA: FetchingAPIDATA = FetchingAPIDATA()) {
    suspend fun getFanArtsHot(): List<SubRedditData> {
        return fetchingAPIDATA.getFanArtsHot()
    }

    suspend fun getFanArtsRelevance(): List<SubRedditData> {
        return fetchingAPIDATA.getFanArtsRelevance()
    }

    suspend fun getNewsRelevance(): List<SubRedditData> {
        return fetchingAPIDATA.getNewsRelevance()
    }

    suspend fun getNewsHot(): List<SubRedditData> {
        return fetchingAPIDATA.getNewsHot()
    }

    suspend fun getImagesHot(): List<SubRedditData> {
        return fetchingAPIDATA.getImagesHot()
    }

    suspend fun getImagesRelevance(): List<SubRedditData> {
        return fetchingAPIDATA.getImagesRelevance()
    }
}