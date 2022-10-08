package com.sakethh.arara.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.home.selectedChipStuff.SelectedChipScreenRepo
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import kotlinx.coroutines.*
import java.util.Calendar

@Suppress("LocalVariableName", "PropertyName")
class HomeScreenViewModel(private val selectedChipScreenRepo: SelectedChipScreenRepo = SelectedChipScreenRepo()) :
    ViewModel() {
    val currentTime = mutableStateOf("")
    val listForHeader = mutableListOf<String>("Warrior-arts", "News", "Images")
    val footerGIFURL = mutableStateOf("")
    val currentTimeLoaded = mutableStateOf(false)
    val fanArtsHotData = mutableStateOf<List<SubRedditData>>(
        emptyList()
    )
    val fanArtsRelevanceData = mutableStateOf<List<SubRedditData>>(emptyList())
    val newsHotData = mutableStateOf<List<SubRedditData>>(emptyList())
    val newsRelevanceData = mutableStateOf<List<SubRedditData>>(emptyList())
    val imagesHotData = mutableStateOf<List<SubRedditData>>(emptyList())
    val imagesRelevanceData = mutableStateOf<List<SubRedditData>>(emptyList())

    object Utils {
        val selectedTextForHomeScreen = mutableStateOf("")
        val nonIndexedValue = mutableStateOf(-69)
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val _fanArtsHotData = getFanArtsHot()
            fanArtsHotData.value=_fanArtsHotData
            val _fanArtsRelevanceData = getFanArtsRelevance()
            fanArtsRelevanceData.value=_fanArtsRelevanceData
            val _newsHotData = getNewsRelevance()
            newsHotData.value=_newsHotData
            val _newsRelevanceData = getNewsHot()
            newsRelevanceData.value=_newsRelevanceData
            val _imagesHotData = getImagesHot()
            imagesHotData.value=_imagesHotData
            val _imagesRelevanceData = getImagesRelevance()
            imagesRelevanceData.value=_imagesRelevanceData
            when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                in 0..4 -> {
                    currentTime.value = "Still Awake?"
                }
                in 5..11 -> {
                    currentTime.value = "Good Morning"
                }
                in 12..15 -> {
                    currentTime.value = "Good Afternoon"
                }
                in 16..22 -> {
                    currentTime.value = "Good Evening"
                }
                in 23 downTo 0 -> {
                    currentTime.value = "Good Night?"
                }
                else -> {
                    currentTime.value = "Everything Fine?"
                }
            }
        }
    }

    private suspend fun getFanArtsHot(): List<SubRedditData> {
        return selectedChipScreenRepo.getFanArtsHot()
    }

    private suspend fun getFanArtsRelevance(): List<SubRedditData> {
        return selectedChipScreenRepo.getFanArtsRelevance()
    }

    private suspend fun getNewsRelevance(): List<SubRedditData> {
        return selectedChipScreenRepo.getNewsRelevance()
    }

    private suspend fun getNewsHot(): List<SubRedditData> {
        return selectedChipScreenRepo.getNewsHot()
    }
    suspend fun getImagesHot(): List<SubRedditData> {
        return selectedChipScreenRepo.getImagesHot()
    }
    suspend fun getImagesRelevance(): List<SubRedditData> {
        return selectedChipScreenRepo.getImagesRelevance()
    }
}