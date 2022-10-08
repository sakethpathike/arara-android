package com.sakethh.arara.home.selectedChipStuff

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("LocalVariableName")
class SelectedChipScreenViewModel(private val selectedChipScreenRepo: SelectedChipScreenRepo = SelectedChipScreenRepo()) :
    ViewModel() {
    val imageIsLoading = mutableStateOf(false)
    object UIData{
        val dropDownActivated= mutableStateOf(false)
    }
}