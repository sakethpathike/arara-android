@file:Suppress("LocalVariableName")

package com.sakethh.arara.api

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.sakethh.arara.Constants
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import com.sakethh.arara.unreleased.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class FetchingAPIDATA() {
    private lateinit var apiData: FetchingAPIDATAInterface
    private fun connectToNetwork() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(UnreleasedCache.okHttpClient)
            .build()
        apiData = retrofitBuilder.create(FetchingAPIDATAInterface::class.java)
    }

    init {
        connectToNetwork()
    }

    suspend fun getSongsData(): List<List<UnreleasedResponse>> {
        val songsData = mutableListOf<Deferred<List<UnreleasedResponse>>>()
        coroutineScope {
            val getSongsData =
                async { apiData.getSongsData() } // calling data asynchronously { 'apiData' is an interface for Retrofit Instance }
            songsData.add(getSongsData)
        }
        return songsData.awaitAll()
    }

    // accessing the above function --> songsData.value.component1()
    suspend fun getUnreleasedFooterImg(): List<UnreleasedFooterImage> {
        return apiData.getUnreleasedFooterImg()
    }

    suspend fun getUnreleasedHeaderImg(): List<UnreleasedArtwork> {
        return apiData.getUnreleasedHeaderImg()
    }

    suspend fun getMusicLoadingGIF(): List<MusicLoadingGIF> {
        return apiData.getMusicLoadingGIF()
    }

    suspend fun getMusicPlayingGIF(): List<MusicPlayingGIF> {
        return apiData.getMusicPlayingGIF()
    }

    suspend fun getFanArtsHot(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val getFanArtsData = async { apiData.getFanArtsHot() }
            fanArtsData.add(getFanArtsData)
        }
        return fanArtsData.awaitAll()
    }

    suspend fun getFanArtsRelevance(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _fanartsData = async { apiData.getFanArtsRelevance() }
            fanArtsData.add(_fanartsData)
        }
        return fanArtsData.awaitAll()
    }

    suspend fun getNewsHot(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsHot() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getNewsRelevance(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsRelevance() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getImagesHot(): List<SubRedditData> {
        val data = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _data = async { apiData.getImagesHot() }
            data.add(_data)
        }
        return data.awaitAll()
    }

    suspend fun getImagesRelevance(): List<SubRedditData> {
        val data = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _data = async { apiData.getImagesRelevance() }
            data.add(_data)
        }
        return data.awaitAll()
    }

    suspend fun getImagesTopAllTime(): List<SubRedditData> {
        val data = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _data = async { apiData.getImagesTopAllTime() }
            data.add(_data)
        }
        return data.awaitAll()
    }

    suspend fun getNewsTopAllTime(): List<SubRedditData> {
        val data = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _data = async { apiData.getNewsTopAllTime() }
            data.add(_data)
        }
        return data.awaitAll()
    }

    suspend fun getFanartsTopAllTime(): List<SubRedditData> {
        val data = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _data = async { apiData.getFanArtsTopAllTime() }
            data.add(_data)
        }
        return data.awaitAll()
    }

    interface FetchingAPIDATAInterface {
        @GET(Constants.UNRELEASED)
        suspend fun getSongsData(): List<UnreleasedResponse>

        @GET(Constants.UNRELEASED_FOOTER_IMG_URL)
        suspend fun getUnreleasedFooterImg(): List<UnreleasedFooterImage>

        @GET(Constants.UNRELEASED_HEADER_IMG_URL)
        suspend fun getUnreleasedHeaderImg(): List<UnreleasedArtwork>

        @GET(Constants.MUSIC_LOADING_GIF)
        suspend fun getMusicLoadingGIF(): List<MusicLoadingGIF>

        @GET(Constants.MUSIC_PLAYING_GIF)
        suspend fun getMusicPlayingGIF(): List<MusicPlayingGIF>

        @GET(Constants.SUBREDDIT_FANARTS_HOT)
        suspend fun getFanArtsHot(): SubRedditData

        @GET(Constants.SUBREDDIT_NEWS_HOT)
        suspend fun getNewsHot(): SubRedditData

        @GET(Constants.SUBREDDIT_IMAGES_HOT)
        suspend fun getImagesHot(): SubRedditData

        @GET(Constants.SUBREDDIT_FANARTS_Relevance)
        suspend fun getFanArtsRelevance(): SubRedditData

        @GET(Constants.SUBREDDIT_NEWS_Relevance)
        suspend fun getNewsRelevance(): SubRedditData

        @GET(Constants.SUBREDDIT_IMAGES_Relevance)
        suspend fun getImagesRelevance(): SubRedditData

        @GET(Constants.SUBREDDIT_IMAGES_TOP_ALL_TIME)
        suspend fun getImagesTopAllTime(): SubRedditData

        @GET(Constants.SUBREDDIT_FANARTS_TOP_ALL_TIME)
        suspend fun getFanArtsTopAllTime(): SubRedditData

        @GET(Constants.SUBREDDIT_NEWS_TOP_ALL_TIME)
        suspend fun getNewsTopAllTime(): SubRedditData
    }
}