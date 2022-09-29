package com.sakethh.arara.unreleased

import android.text.format.DateUtils
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakethh.arara.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnreleasedViewModel(private val unreleasedRepo: UnreleasedRepo = UnreleasedRepo()) :
    ViewModel() {
    val rememberData: MutableState<List<UnreleasedResponse>> = mutableStateOf(emptyList())
    val rememberUnreleasedFooterImg: MutableState<List<UnreleasedFooterImage>> =
        mutableStateOf(emptyList())
    val rememberUnreleasedHeaderImg: MutableState<List<UnreleasedArtwork>> =
        mutableStateOf(emptyList())
    val rememberMusicPlayerImgURL = mutableStateOf("")
    val rememberMusicPlayerHDImgURL = mutableStateOf("")
    val rememberMusicPlayerTitle = mutableStateOf("")
    val rememberMusicPlayerLyrics = mutableStateOf("")
    val rememberMusicPlayerDescription = mutableStateOf("")
    val rememberMusicPlayerDescriptionBy = mutableStateOf("")
    val rememberMusicPlayerDescriptionOrigin = mutableStateOf("")
    val rememberMusicPlayerArtworkBy = mutableStateOf("")
    val rememberMusicPlayerLoadingGIF: MutableState< List<MusicLoadingGIF>> = mutableStateOf(emptyList())
    val rememberMusicPlayerPlayingGIF : MutableState<  List<MusicPlayingGIF>> = mutableStateOf(emptyList())
    val rememberMusicPlayerControlImg = listOf(R.drawable.play, R.drawable.pause)
    val rememberMusicPlayerControl = mutableStateOf(false)
    val musicPlayerActivate = mutableStateOf(false)
    val musicAudioURL = mutableStateOf("")
    val musicPlayerVisibility= mutableStateOf(false)
    val currentLoadingStatusGIFURL = mutableStateOf("")
    val currentSongMaxDuration= mutableStateOf(0)
    val currentSongCurrentDuration= mutableStateOf(0)
    val currentSongIsPlaying= mutableStateOf(false)
    object MediaPlayer {
        val mediaPlayer = android.media.MediaPlayer()
        val musicCompleted = mutableStateOf(false)
    }
    fun musicDuration(ms:Long): MutableState<String> {
        val duration= mutableStateOf("")
        viewModelScope.launch {
            val conversion=DateUtils.formatElapsedTime(ms/1000)
            duration.value=conversion.toString()
        }
        return duration
    }
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val songsData = getSongsData()
            rememberData.value = songsData.component1()
            val headerData = getUnreleasedHeaderImg()
            rememberUnreleasedHeaderImg.value = headerData
            val footerData = getUnreleasedFooterImg()
            rememberUnreleasedFooterImg.value = footerData
            val loadingGIF = getMusicLoadingGIF()
            rememberMusicPlayerLoadingGIF.value=loadingGIF
            val playingGIF = getMusicPlayingGIF()
            rememberMusicPlayerPlayingGIF.value=playingGIF
        }

    }

    private suspend fun getUnreleasedFooterImg(): List<UnreleasedFooterImage> {
        return unreleasedRepo.getUnreleasedFooterImg()
    }

    private suspend fun getUnreleasedHeaderImg(): List<UnreleasedArtwork> {
        return unreleasedRepo.getUnreleasedHeaderImg()
    }

    private suspend fun getSongsData(): List<List<UnreleasedResponse>> {
        return unreleasedRepo.getSongsData()
    }
    suspend fun getMusicLoadingGIF(): List<MusicLoadingGIF> {
        return unreleasedRepo.getMusicLoadingGIF()
    }

    suspend fun getMusicPlayingGIF(): List<MusicPlayingGIF> {
        return unreleasedRepo.getMusicPlayingGIF()
    }
}