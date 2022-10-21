package com.sakethh.arara.home.selectedChipStuff

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.BookMarksDB
import com.sakethh.arara.bookmarks.BookMarkRepo
import kotlinx.coroutines.*

@Suppress("LocalVariableName")
class SelectedChipScreenViewModel(private val bookMarkRepo: BookMarkRepo = BookMarkRepo()) :
    ViewModel() {
    val imageIsLoading = mutableStateOf(false)

    object BookMarkedDataUtils {
        val realmDBObject: BookMarksDB = BookMarksDB()
        val toastMessage = mutableStateOf("")
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    fun addToDB() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            bookMarkRepo.writeToDB(realmDBObject = BookMarkedDataUtils.realmDBObject)
        }
    }
}