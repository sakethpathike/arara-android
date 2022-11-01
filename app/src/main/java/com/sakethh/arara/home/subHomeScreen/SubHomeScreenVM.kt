package com.sakethh.arara.home.subHomeScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.api.FetchingAPIDATA
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditDataItem
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SubHomeScreenVM(private val fetchingAPIDATA: FetchingAPIDATA = FetchingAPIDATA()) :
    ViewModel() {
    val topList = listOf("All Time","Past 24 Hours","Past Hour","Past Month","Past Week","Past Year")
    object Utils{
        val selectedTextFromSubHomeScreenTopSection= mutableStateOf("All Time")
    }
    object Fanarts {
        val relevance = mutableStateOf<List<SubRedditData>>(emptyList())
        val topAllTime = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPast24Hours = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastHour = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastMonth = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastWeek = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastYear = mutableStateOf<List<SubRedditData>>(emptyList())
        val hot = mutableStateOf<List<SubRedditData>>(emptyList())
        val isDataLoaded = mutableStateOf(false)
        init {
            SubHomeScreenVM().viewModelScope.launch {
                val relevance = async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsRelevance() }
                val topAllTime = async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsTopAllTime() }
                val topPast24Hours =
                    async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsTopPast24Hours() }
                val topPastHour =
                    async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsTopPastHour() }
                val topPastMonth =
                    async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsTopPastMonth() }
                val topPastWeek =
                    async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsTopPastWeek() }
                val topPastYear =
                    async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsTopPastYear() }
                val hot =
                    async { SubHomeScreenVM().fetchingAPIDATA.getFanArtsHot() }
                Fanarts.relevance.value = relevance.await()
                Fanarts.topAllTime.value = topAllTime.await()
                Fanarts.topPast24Hours.value = topPast24Hours.await()
                Fanarts.topPastHour.value = topPastHour.await()
                Fanarts.topPastMonth.value = topPastMonth.await()
                Fanarts.topPastWeek.value = topPastWeek.await()
                Fanarts.topPastYear.value = topPastYear.await()
                Fanarts.hot.value = hot.await()
                isDataLoaded.value=true
            }
        }
    }

    object Images {
        val relevance = mutableStateOf<List<SubRedditData>>(emptyList())
        val topAllTime = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPast24Hours = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastHour = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastMonth = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastWeek = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastYear = mutableStateOf<List<SubRedditData>>(emptyList())
        val hot = mutableStateOf<List<SubRedditData>>(emptyList())
        val isDataLoaded = mutableStateOf(false)

        init {
            SubHomeScreenVM().viewModelScope.launch {
                val relevance = async { SubHomeScreenVM().fetchingAPIDATA.getImagesRelevance() }
                val topAllTime = async { SubHomeScreenVM().fetchingAPIDATA.getImagesTopAllTime() }
                val topPast24Hours =
                    async { SubHomeScreenVM().fetchingAPIDATA.getImagesTopPast24Hours() }
                val topPastHour =
                    async { SubHomeScreenVM().fetchingAPIDATA.getImagesTopPastHour() }
                val topPastMonth =
                    async { SubHomeScreenVM().fetchingAPIDATA.getImagesTopPastMonth() }
                val topPastWeek =
                    async { SubHomeScreenVM().fetchingAPIDATA.getImagesTopPastWeek() }
                val hot =
                    async { SubHomeScreenVM().fetchingAPIDATA.getImagesHot() }
                val topPastYear =
                    async { SubHomeScreenVM().fetchingAPIDATA.getImagesTopPastYear() }
                Images.relevance.value = relevance.await()
                Images.topAllTime.value = topAllTime.await()
                Images.topPast24Hours.value = topPast24Hours.await()
                Images.topPastHour.value = topPastHour.await()
                Images.topPastMonth.value = topPastMonth.await()
                Images.topPastWeek.value = topPastWeek.await()
                Images.topPastYear.value = topPastYear.await()
                Images.hot.value = hot.await()
                Images.isDataLoaded.value=true
            }
        }
    }

    object News {
        val relevance = mutableStateOf<List<SubRedditData>>(emptyList())
        val topAllTime = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPast24Hours = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastHour = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastMonth = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastWeek = mutableStateOf<List<SubRedditData>>(emptyList())
        val topPastYear = mutableStateOf<List<SubRedditData>>(emptyList())
        val hot = mutableStateOf<List<SubRedditData>>(emptyList())
        val isDataLoaded = mutableStateOf(false)

        init {
            SubHomeScreenVM().viewModelScope.launch {
                val relevance = async { SubHomeScreenVM().fetchingAPIDATA.getNewsRelevance() }
                val topAllTime = async { SubHomeScreenVM().fetchingAPIDATA.getNewsTopAllTime() }
                val topPast24Hours =
                    async { SubHomeScreenVM().fetchingAPIDATA.getNewsTopPast24Hours() }
                val topPastHour =
                    async { SubHomeScreenVM().fetchingAPIDATA.getNewsTopPastHour() }
                val topPastMonth =
                    async { SubHomeScreenVM().fetchingAPIDATA.getNewsTopPastMonth() }
                val topPastWeek =
                    async { SubHomeScreenVM().fetchingAPIDATA.getNewsTopPastWeek() }
                val topPastYear =
                    async { SubHomeScreenVM().fetchingAPIDATA.getNewsTopPastYear() }
                val hot =
                    async { SubHomeScreenVM().fetchingAPIDATA.getNewsHot() }
                News.relevance.value = relevance.await()
                News.topAllTime.value = topAllTime.await()
                News.topPast24Hours.value = topPast24Hours.await()
                News.topPastHour.value = topPastHour.await()
                News.topPastMonth.value = topPastMonth.await()
                News.topPastWeek.value = topPastWeek.await()
                News.topPastYear.value = topPastYear.await()
                News.hot.value = hot.await()
                News.isDataLoaded.value=true
            }
        }
    }
}