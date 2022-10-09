package com.sakethh.arara.home.selectedChipStuff

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("LocalVariableName")
class SelectedChipScreenViewModel() :
    ViewModel() {
    val imageIsLoading = mutableStateOf(false)
    val bookMarkedTitle = mutableStateOf<String?>(null)
    val bookMarkedAuthor = mutableStateOf<String?>(null)
    val bookMarkedImgUrl = mutableStateOf<String?>(null)
    val selectedChipScreenRealmDB: SelectedChipScreenRealmDB = SelectedChipScreenRealmDB
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    fun writeToDB() {
          viewModelScope.launch(Dispatchers.IO+ coroutineExceptionHandler) {
              selectedChipScreenRealmDB.writeToDB(
                  bookMarked = true,
                  author = bookMarkedAuthor.value,
                  imgUrl = bookMarkedImgUrl.value,
                  title = bookMarkedTitle.value
              )
          }
      }
}