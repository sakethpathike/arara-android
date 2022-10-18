package com.sakethh.arara.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.home.selectedChipStuff.SelectedChipScreenAPIRepo
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import com.sakethh.arara.unreleased.UnreleasedFooterImage
import com.sakethh.arara.unreleased.UnreleasedRepo
import kotlinx.coroutines.*
import java.util.Calendar
import com.sakethh.arara.R
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.fanArtsHotData
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.fanArtsRelevanceData
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.imagesHotData
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.imagesRelevanceData
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.newsHotData
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.newsRelevanceData
import com.sakethh.arara.home.selectedChipStuff.apiData.Data
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditDataItem

@Suppress("LocalVariableName", "PropertyName")
class HomeScreenViewModel(
    private val selectedChipScreenRepo: SelectedChipScreenAPIRepo = SelectedChipScreenAPIRepo(),
    private val unreleasedRepo: UnreleasedRepo = UnreleasedRepo()
) :
    ViewModel() {
    val currentTime = mutableStateOf("")
    val listForHeader = mutableListOf<String>("Warrior-arts", "News", "Images")

    object CloseButtonUtils {
        val closeButtonIcon = mutableListOf(R.drawable.close_icon)
        val isCloseButtonPressed = mutableStateOf(false)
    }

    object RetrievedSubRedditData {
        val currentTimeIsLoaded = mutableStateOf(false)
        val footerGIFURL = mutableStateOf<List<UnreleasedFooterImage>>(emptyList())
        val fanArtsHotData = mutableStateOf<List<SubRedditData>>(
            emptyList()
        )
        val fanArtsRelevanceData = mutableStateOf<List<SubRedditData>>(emptyList())
        val newsHotData = mutableStateOf<List<SubRedditData>>(emptyList())
        val newsRelevanceData = mutableStateOf<List<SubRedditData>>(emptyList())
        val imagesHotData = mutableStateOf<List<SubRedditData>>(emptyList())
        val imagesRelevanceData = mutableStateOf<List<SubRedditData>>(emptyList())
    }

    object Utils {
        val selectedTextForHomeScreen = mutableStateOf("")
        val nonIndexedValue = mutableStateOf(-69)
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            withContext(Dispatchers.Main) {
                val _fanArtsHotData = async { getFanArtsHot() }
                val _fanArtsRelevanceData = async { getFanArtsRelevance() }
                val _newsHotData = async { getNewsRelevance() }
                val _newsRelevanceData = async { getNewsHot() }
                val _imagesHotData = async { getImagesHot() }
                val _imagesRelevanceData = async { getImagesRelevance() }
                imagesHotData.value = _imagesHotData.await()
                newsRelevanceData.value = _newsRelevanceData.await()
                newsHotData.value = _newsHotData.await()
                fanArtsRelevanceData.value = _fanArtsRelevanceData.await()
                fanArtsHotData.value = _fanArtsHotData.await()
                imagesRelevanceData.value = _imagesRelevanceData.await()
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
                RetrievedSubRedditData.footerGIFURL.value = getUnreleasedFooterImg()
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

    private suspend fun getImagesHot(): List<SubRedditData> {
        return selectedChipScreenRepo.getImagesHot()
    }

    private suspend fun getImagesRelevance(): List<SubRedditData> {
        return selectedChipScreenRepo.getImagesRelevance()
    }

    private suspend fun getUnreleasedFooterImg(): List<UnreleasedFooterImage> {
        return unreleasedRepo.getUnreleasedFooterImg()
    }
}

// author = "Author",
//                is_video = false,
//                permalink = "",
//                title = "And we become night time dreamers, street walkers and small talkers when we should be day dreamers",
//                url = ""