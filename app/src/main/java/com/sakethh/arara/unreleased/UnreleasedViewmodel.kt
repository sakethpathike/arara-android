package com.sakethh.arara.unreleased

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class UnreleasedViewModel(private val unreleasedRepo: UnreleasedRepo = UnreleasedRepo()) :
    ViewModel() {
    private val coroutineExceptionHandler= CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val songsData = getSongsData()
            rememberData.value = songsData
        }
    }

    val rememberData: MutableState<List<UnreleasedResponse>> = mutableStateOf(emptyList())

    private suspend fun getSongsData(): List<UnreleasedResponse> {
        return unreleasedRepo.getSongsData()
    }
}