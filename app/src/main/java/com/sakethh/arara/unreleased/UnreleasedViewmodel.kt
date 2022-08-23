package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnreleasedViewModel(private val unreleasedRepo: UnreleasedRepo = UnreleasedRepo()) :
    ViewModel() {
    val rememberData: MutableState<List<UnreleasedResponse>> = mutableStateOf(emptyList())
    val rememberUnreleasedFooterImg:MutableState<List<UnreleasedFooterImage>> = mutableStateOf(emptyList())
    val rememberUnreleasedHeaderImg:MutableState<List<UnreleasedArtwork>> = mutableStateOf(emptyList())
    private val coroutineExceptionHandler= CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val songsData = getSongsData()
            rememberData.value = songsData
            val headerData=getUnreleasedHeaderImg()
            rememberUnreleasedHeaderImg.value=headerData
            val footerData=getUnreleasedFooterImg()
            rememberUnreleasedFooterImg.value=footerData
        }

    }
    private suspend fun getUnreleasedFooterImg():List<UnreleasedFooterImage>{
        return unreleasedRepo.getUnreleasedFooterImg()
    }
    private suspend fun getUnreleasedHeaderImg():List<UnreleasedArtwork>{
        return unreleasedRepo.getUnreleasedHeaderImg()
    }
    private suspend fun getSongsData(): List<UnreleasedResponse> {
        return unreleasedRepo.getSongsData()
    }
}