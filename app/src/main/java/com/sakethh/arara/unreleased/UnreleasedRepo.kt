package com.sakethh.arara.unreleased

import com.sakethh.arara.api.FetchingAPIDATA

class UnreleasedRepo(private val fetchingAPIDATA: FetchingAPIDATA = FetchingAPIDATA()) {
    suspend fun getSongsData(): List<List<UnreleasedResponse>> {
        return fetchingAPIDATA.getSongsData()
    }

    suspend fun getUnreleasedFooterImg(): List<UnreleasedFooterImage> {
        return fetchingAPIDATA.getUnreleasedFooterImg()
    }

    suspend fun getUnreleasedHeaderImg(): List<UnreleasedArtwork> {
        return fetchingAPIDATA.getUnreleasedHeaderImg()
    }

    suspend fun getMusicLoadingGIF(): List<MusicLoadingGIF> {
        return fetchingAPIDATA.getMusicLoadingGIF()
    }

    suspend fun getMusicPlayingGIF(): List<MusicPlayingGIF> {
        return fetchingAPIDATA.getMusicPlayingGIF()
    }
}

