package com.sakethh.arara.bookmarks

import com.sakethh.arara.BookMarksDB
import com.sakethh.arara.home.selectedChipStuff.SelectedChipScreenViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow

class BookMarkRepo(private val realmObject: RealmObject = BookMarksDB()) {

    private val realmConfiguration = RealmConfiguration.create(setOf(realmObject::class))
    val realm = Realm.open(realmConfiguration)
    suspend fun writeToDB(realmDBObject: BookMarksDB) {
        realm.write {
            try {
                this.copyToRealm(realmDBObject)
                SelectedChipScreenViewModel.BookMarkedDataUtils.toastMessage.value = "Done:)"
            }catch (e:java.lang.IllegalArgumentException){
                SelectedChipScreenViewModel.BookMarkedDataUtils.toastMessage.value = "you have already bookmarked this way before:)"
            }catch (e:java.lang.IllegalStateException){
                SelectedChipScreenViewModel.BookMarkedDataUtils.toastMessage.value = "wait a minute, writing previous bookmarked data to local database:)"
            }
        }
    }

    fun readFromDB(): Flow<ResultsChange<BookMarksDB>> {
        return realm.query<BookMarksDB>().asFlow()
    }

    fun deleteFromDB(imgUrl: String) {
        realm.writeBlocking {
            val deleteThisBro = this.query<BookMarksDB>("objectKey = $0", imgUrl).first().find()
            if (deleteThisBro != null) {
                delete(deleteThisBro)
            }
        }
    }
}
/*
java.lang.IllegalArgumentException: Failed to create object of type 'RealmDBObject': RealmCoreException([33]: Object with this primary key already exists)

java.lang.IllegalStateException: Cannot begin the write transaction: RealmCoreException([5]: The Realm is already in a write transaction)


*/
