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

object SelectedChipScreenRealmDB {
    private val realmConfiguration = RealmConfiguration.create(schema = setOf(RealmDBObject::class))
    val realm = Realm.open(realmConfiguration)
    suspend fun writeToDB(
        author: String?,
        bookMarked: Boolean,
        imgUrl: String?,
        title: String?
    ): Realm {
        coroutineScope {
            realm.write {
                this.copyToRealm(RealmDBObject().apply {
                    this.author = author
                    this.bookMarked = bookMarked
                    this.imgURL = imgUrl
                    this.title = title
                })
            }
        }
        return realm
    }
}