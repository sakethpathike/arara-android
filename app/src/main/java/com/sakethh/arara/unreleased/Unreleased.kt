package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.AudioManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.*
import com.sakethh.arara.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen(itemOnClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ) { false }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedData=unreleasedViewModel.rememberData
    val headerData = UnreleasedViewModel.MediaPlayer.rememberUnreleasedHeaderImg.value
    val footerData = UnreleasedViewModel.MediaPlayer.rememberUnreleasedFooterImg.value
    val musicPlayerImgURL = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerImgURL
    val musicPlayerHDImgURL = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerHDImgURL
    val musicPlayerTitle = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerTitle
    val unreleasedLyricsForPlayer = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerLyrics
    val rememberMusicPlayerDescription = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescription
    val rememberMusicPlayerDescriptionBy = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescriptionBy
    val rememberMusicPlayerArtworkBy = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerArtworkBy
    val rememberMusicPlayerDescriptionOrigin =
        UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescriptionOrigin
    val audioUrl=UnreleasedViewModel.MediaPlayer.musicAudioURL
    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                Text(
                    text = "Unreleased",
                    style = MaterialTheme.typography.titleMedium,
                    color = md_theme_dark_onTertiaryContainer
                )
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_onTertiary)
        )
    }) { contentPadding ->
        LazyColumn(
            modifier = Modifier.background(md_theme_dark_onTertiary).padding(bottom = 30.dp),
            contentPadding = contentPadding,
        ) {

            items(headerData) { data ->
                ArtBoard(data.artwork)
            }

            items(unreleasedData.value) { data ->
                SongThing1(
                    songName = data.songName,
                    specificArtwork = data.imgURL
                ) {
                    musicPlayerImgURL.value = data.imgURL
                    musicPlayerTitle.value = data.songName
                    unreleasedLyricsForPlayer.value = data.lyrics
                    rememberMusicPlayerDescription.value = data.songDescription
                    rememberMusicPlayerDescriptionBy.value = data.descriptionBy
                    rememberMusicPlayerDescriptionOrigin.value = data.descriptionOrigin
                    rememberMusicPlayerArtworkBy.value = data.specificArtworkBy
                    audioUrl.value=data.audioLink
                    musicPlayerHDImgURL.value=data.imgURLHD
                    itemOnClick()
                }
            }
            items(footerData) { data ->
                if(UnreleasedViewModel.MediaPlayer.musicPlayerActivate.value){
                    GIFThing(
                        imgURL = data.footerImg, modifier = Modifier
                            .background(md_theme_dark_surface)
                            .padding(bottom = 150.dp)
                            .fillMaxWidth()
                            .height(70.dp)
                    )
                }else{
                    GIFThing(
                        imgURL = data.footerImg, modifier = Modifier
                            .background(md_theme_dark_surface)
                            .padding(bottom = 50.dp)
                            .fillMaxWidth()
                            .height(70.dp)
                    )
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUnreleasedScreen() {
    val systemUIController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = coroutineScope) {
        coroutineScope.launch {
            systemUIController.setStatusBarColor(color = md_theme_dark_onTertiary)
        }
    }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val musicControlBoolean = rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControlImg }
    val currentControlIcon = rememberSaveable { mutableListOf(0, 1) }
    val currentAudioURL = UnreleasedViewModel.MediaPlayer.musicAudioURL
    rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescriptionBy }
    rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescriptionOrigin }
    UnreleasedViewModel.MediaPlayer.rememberMusicPlayerArtworkBy
    val currentGIFURL = rememberSaveable { UnreleasedViewModel.MediaPlayer.currentLoadingStatusGIFURL }
    val currentSongMaxDuration = rememberSaveable { UnreleasedViewModel.MediaPlayer.currentSongMaxDuration }
    val currentSongCurrentDuration =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.currentSongCurrentDuration }
    rememberSaveable { UnreleasedViewModel.MediaPlayer.currentSongIsPlaying }
    if (musicControlBoolean.value) {
        val playIcon = rememberMusicPlayerControlImg[0]  //play icon
        currentControlIcon[0] = playIcon
    } else {
        val pauseIcon = rememberMusicPlayerControlImg[1] //pause icon
        currentControlIcon[0] = pauseIcon
    }
        UnreleasedScreen {
            UnreleasedViewModel.MediaPlayer.musicPlayerActivate.value = true
            UnreleasedViewModel.MediaPlayer.musicCompleted.value = false
            UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControl.value = false
            UnreleasedViewModel.MediaPlayer.currentSongIsPlaying.value = false
            currentGIFURL.value =
                UnreleasedViewModel.MediaPlayer.rememberMusicPlayerLoadingGIF.component1()[0].gifURL
            UnreleasedViewModel.MediaPlayer.musicPlayerVisibility.value = false
            if (Build.VERSION.SDK_INT <= 26) {
                UnreleasedViewModel.MediaPlayer.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            } else {
                UnreleasedViewModel.MediaPlayer.mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build()
                )
            }
            if (UnreleasedViewModel.MediaPlayer.mediaPlayer.isPlaying || !UnreleasedViewModel.MediaPlayer.mediaPlayer.isPlaying) {
                UnreleasedViewModel.MediaPlayer.mediaPlayer.stop()
                UnreleasedViewModel.MediaPlayer.mediaPlayer.reset().also {
                    UnreleasedViewModel.MediaPlayer.mediaPlayer.setDataSource(currentAudioURL.value)
                    UnreleasedViewModel.MediaPlayer.mediaPlayer.prepareAsync()
                    UnreleasedViewModel.MediaPlayer.mediaPlayer.setOnPreparedListener {
                        try {
                            it.start()
                            currentSongMaxDuration.value = it.duration
                            currentSongCurrentDuration.value = it.currentPosition
                        } catch (e: Exception) {
                            currentGIFURL.value =
                                Constants.MUSIC_ERROR_GIF
                        }
                        if (it.isPlaying) {
                            currentGIFURL.value =
                                UnreleasedViewModel.MediaPlayer.rememberMusicPlayerPlayingGIF.component1()[0].gifURL
                            UnreleasedViewModel.MediaPlayer.musicPlayerVisibility.value = true
                            UnreleasedViewModel.MediaPlayer.currentSongIsPlaying.value = true
                        }
                        it.setOnCompletionListener {
                            UnreleasedViewModel.MediaPlayer.musicCompleted.value = true
                            UnreleasedViewModel.MediaPlayer.musicPlayerVisibility.value = false
                            UnreleasedViewModel.MediaPlayer.currentLoadingStatusGIFURL.value =
                                Constants.MUSIC_ERROR_GIF
                            UnreleasedViewModel.MediaPlayer.currentSongIsPlaying.value = false
                        }
                    }
                }
            }
        }

}