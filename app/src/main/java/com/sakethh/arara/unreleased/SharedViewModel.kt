package com.sakethh.arara.unreleased

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.RawValue

class SharedViewModel:ViewModel() {
    var dataForCurrentMusicScreen= mutableStateOf<UnreleasedScreenCurrentData?>(null)
    private set

    fun data(data:UnreleasedScreenCurrentData){
        dataForCurrentMusicScreen.value=data
    }
}