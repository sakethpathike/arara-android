package com.sakethh.arara.unreleased

import android.content.Context
import com.sakethh.arara.api.UnreleasedAPIThing

class UnreleasedRepo(private val unreleasedAPIThing: UnreleasedAPIThing = UnreleasedAPIThing()) {
    suspend fun getSongsData():List<UnreleasedResponse> {
        return unreleasedAPIThing.getSongsData()
    }
    suspend fun getUnreleasedFooterImg():List<UnreleasedFooterImage>{
        return unreleasedAPIThing.getUnreleasedFooterImg()
    }
    suspend fun getUnreleasedHeaderImg():List<UnreleasedArtwork>{
        return unreleasedAPIThing.getUnreleasedHeaderImg()
    }
}

