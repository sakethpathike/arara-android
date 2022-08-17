package com.sakethh.arara.unreleased

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UnreleasedViewModel(private val unreleasedRepo: UnreleasedRepo = UnreleasedRepo()) :
    ViewModel() {
    val rememberData: MutableState<List<UnreleasedResponse>> =
        mutableStateOf(emptyList<UnreleasedResponse>())

    init {
        viewModelScope.launch(Dispatchers.IO){
            rememberData.value = getSongsData()
        }
    }

    private suspend fun getSongsData(): List<UnreleasedResponse> {
        return unreleasedRepo.getSongsData().unreleasedResponse
    }
}