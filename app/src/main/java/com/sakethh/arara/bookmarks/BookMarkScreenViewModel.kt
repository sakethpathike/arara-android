package com.sakethh.arara.bookmarks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.BookMarksDB
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.*

@Suppress("LocalVariableName", "PropertyName")
class BookMarkScreenViewModel(val bookMarkRepo: BookMarkRepo = BookMarkRepo()) :
    ViewModel() {
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private val realmResults = mutableStateListOf<RealmResults<BookMarksDB>>()
    val bookMarkedData : List<RealmResults<BookMarksDB>> = realmResults
    val bookMarkedDataSize = mutableStateOf(bookMarkedData.size)
    init {
        viewModelScope.launch(Dispatchers.IO) {
           withContext(Dispatchers.Main){
               bookMarkRepo.readFromDB().collect { realmList ->
                   bookMarkedDataSize.value = realmList.list.size.also {
                       realmResults.add(realmList.list)
                   }
               }
           }
        }
    }
}