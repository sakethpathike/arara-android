package com.sakethh.arara.unreleased

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnreleasedViewModel(private val unreleasedRepo: UnreleasedRepo = UnreleasedRepo()) :
    ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val songsData = getSongsData()
            rememberData.value = songsData
        }
    }

    val rememberData: MutableState<List<UnreleasedResponse>> =
        mutableStateOf(emptyList<UnreleasedResponse>())

    private suspend fun getSongsData(): List<UnreleasedResponse> {
        return unreleasedRepo.getSongsData().unreleasedResponse
    }
}