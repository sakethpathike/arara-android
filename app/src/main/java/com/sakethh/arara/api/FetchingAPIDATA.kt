@file:Suppress("LocalVariableName")

package com.sakethh.arara.api

import androidx.compose.runtime.mutableStateListOf
import com.sakethh.arara.Constants
import com.sakethh.arara.FanartsURL
import com.sakethh.arara.ImagesURL
import com.sakethh.arara.NewsURL
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
            .client(Caching.okHttpClient)
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
// fanarts0
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

    suspend fun getFanArtsTopAllTime(): List<SubRedditData> {
        val data = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _data = async { apiData.getFanArtsTopAllTime() }
            data.add(_data)
        }
        return data.awaitAll()
    }
    suspend fun getFanArtsTopPast24Hours(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _fanartsData = async { apiData.getFanArtsTopPast24Hours() }
            fanArtsData.add(_fanartsData)
        }
        return fanArtsData.awaitAll()
    }

    suspend fun getFanArtsTopPastHour(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val getFanArtsData = async { apiData.getFanArtsTopPastHour() }
            fanArtsData.add(getFanArtsData)
        }
        return fanArtsData.awaitAll()
    }

    suspend fun getFanArtsTopPastMonth(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _fanartsData = async { apiData.getFanArtsTopPastMonth() }
            fanArtsData.add(_fanartsData)
        }
        return fanArtsData.awaitAll()
    }

    suspend fun getFanArtsTopPastWeek(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val getFanArtsData = async { apiData.getFanArtsTopPastWeek() }
            fanArtsData.add(getFanArtsData)
        }
        return fanArtsData.awaitAll()
    }

    suspend fun getFanArtsTopPastYear(): List<SubRedditData> {
        val fanArtsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _fanartsData = async { apiData.getFanArtsTopPastYear() }
            fanArtsData.add(_fanartsData)
        }
        return fanArtsData.awaitAll()
    }
// news0
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
    suspend fun getNewsTopAllTime(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsTopAllTime() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getNewsTopPast24Hours(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsTopPast24Hours() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }
    suspend fun getNewsTopPastHour(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsTopPastHour() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getNewsTopPastMonth(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsTopPastMonth() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }
    suspend fun getNewsTopPastWeek(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsTopPastWeek() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getNewsTopPastYear(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getNewsTopPastYear() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }
// images0
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

    suspend fun getImagesTopPast24Hours(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getImagesTopPast24Hours() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }
    suspend fun getImagesTopPastHour(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getImagesTopPastHour() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getImagesTopPastMonth(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getImagesTopPastMonth() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }
    suspend fun getImagesTopPastWeek(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getImagesTopPastWeek() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
    }

    suspend fun getImagesTopPastYear(): List<SubRedditData> {
        val newsData = mutableStateListOf<Deferred<SubRedditData>>()
        coroutineScope {
            val _newsData = async { apiData.getImagesTopPastYear() }
            newsData.add(_newsData)
        }
        return newsData.awaitAll()
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
// fanarts0
        @GET(FanartsURL.relevance)
        suspend fun getFanArtsRelevance(): SubRedditData

        @GET(FanartsURL.topAllTime)
        suspend fun getFanArtsTopAllTime(): SubRedditData

        @GET(FanartsURL.topPast24Hours)
        suspend fun getFanArtsTopPast24Hours(): SubRedditData

        @GET(FanartsURL.topPastHour)
        suspend fun getFanArtsTopPastHour(): SubRedditData

        @GET(FanartsURL.topPastMonth)
        suspend fun getFanArtsTopPastMonth(): SubRedditData

        @GET(FanartsURL.topPastWeek)
        suspend fun getFanArtsTopPastWeek(): SubRedditData

        @GET(FanartsURL.topPastYear)
        suspend fun getFanArtsTopPastYear(): SubRedditData

        @GET(FanartsURL.hot)
        suspend fun getFanArtsHot(): SubRedditData

        // news0
        @GET(NewsURL.hot)
        suspend fun getNewsHot(): SubRedditData

        @GET(NewsURL.relevance)
        suspend fun getNewsRelevance(): SubRedditData

        @GET(NewsURL.topAllTime)
        suspend fun getNewsTopAllTime(): SubRedditData

        @GET(NewsURL.topPast24Hours)
        suspend fun getNewsTopPast24Hours(): SubRedditData

        @GET(NewsURL.topPastHour)
        suspend fun getNewsTopPastHour(): SubRedditData

        @GET(NewsURL.topPastMonth)
        suspend fun getNewsTopPastMonth(): SubRedditData

        @GET(NewsURL.topPastWeek)
        suspend fun getNewsTopPastWeek(): SubRedditData

        @GET(NewsURL.topPastYear)
        suspend fun getNewsTopPastYear(): SubRedditData

        // images0
        @GET(ImagesURL.hot)
        suspend fun getImagesHot(): SubRedditData

        @GET(ImagesURL.relevance)
        suspend fun getImagesRelevance(): SubRedditData

        @GET(ImagesURL.topAllTime)
        suspend fun getImagesTopAllTime(): SubRedditData

        @GET(ImagesURL.topPast24Hours)
        suspend fun getImagesTopPast24Hours(): SubRedditData

        @GET(ImagesURL.topPastHour)
        suspend fun getImagesTopPastHour(): SubRedditData

        @GET(ImagesURL.topPastMonth)
        suspend fun getImagesTopPastMonth(): SubRedditData

        @GET(ImagesURL.topPastWeek)
        suspend fun getImagesTopPastWeek(): SubRedditData

        @GET(ImagesURL.topPastYear)
        suspend fun getImagesTopPastYear(): SubRedditData

    }
}