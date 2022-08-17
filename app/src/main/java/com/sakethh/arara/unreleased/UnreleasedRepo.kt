package com.sakethh.arara.unreleased

import com.sakethh.arara.api.UnreleasedAPIThing

class UnreleasedRepo(private val unreleasedAPIThing: UnreleasedAPIThing = UnreleasedAPIThing()) {
    suspend fun getSongsData(): UnreleasedListResponse {
        return unreleasedAPIThing.getSongsData()
    }
}

