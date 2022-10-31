package com.sakethh.arara

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sakethh.arara.unreleased.UnreleasedScreenCurrentData

class SharedViewModel:ViewModel() {
    var dataForCurrentMusicScreen= mutableStateOf<UnreleasedScreenCurrentData?>(null)
    private set
    var _permalink = mutableStateOf("")
        private set

    fun data(data: UnreleasedScreenCurrentData){
        dataForCurrentMusicScreen.value=data
    }
    fun assignPermalink(permalink:String){
        _permalink.value = permalink
    }
    val isBottomNavVisible = mutableStateOf(false)
}