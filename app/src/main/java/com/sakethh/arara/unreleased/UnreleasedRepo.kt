package com.sakethh.arara.unreleased

import com.sakethh.arara.api.UnreleasedAPIThing

class UnreleasedRepo(private val unreleasedAPIThing: UnreleasedAPIThing = UnreleasedAPIThing()) {
    suspend fun getSongsData():List<UnreleasedResponse> {
        return unreleasedAPIThing.getSongsData()
    }
}

