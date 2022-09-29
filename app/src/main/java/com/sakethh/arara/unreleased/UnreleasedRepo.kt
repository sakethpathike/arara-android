package com.sakethh.arara.unreleased

import com.sakethh.arara.api.UnreleasedAPIThing

class UnreleasedRepo(private val unreleasedAPIThing: UnreleasedAPIThing = UnreleasedAPIThing()) {
    suspend fun getSongsData(): List<List<UnreleasedResponse>> {
        return unreleasedAPIThing.getSongsData()
    }

    suspend fun getUnreleasedFooterImg(): List<UnreleasedFooterImage> {
        return unreleasedAPIThing.getUnreleasedFooterImg()
    }

    suspend fun getUnreleasedHeaderImg(): List<UnreleasedArtwork> {
        return unreleasedAPIThing.getUnreleasedHeaderImg()
    }

    suspend fun getMusicLoadingGIF(): List<MusicLoadingGIF> {
        return unreleasedAPIThing.getMusicLoadingGIF()
    }

    suspend fun getMusicPlayingGIF(): List<MusicPlayingGIF> {
        return unreleasedAPIThing.getMusicPlayingGIF()
    }
}

