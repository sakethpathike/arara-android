package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.AudioManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.*
import com.sakethh.arara.home.shimmer
import com.sakethh.arara.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen(itemOnClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ) { false }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedData = unreleasedViewModel.rememberData
    val headerData = UnreleasedViewModel.UnreleasedUtils.rememberUnreleasedHeaderImg.value
    val footerData = UnreleasedViewModel.UnreleasedUtils.rememberUnreleasedFooterImg.value
    val musicPlayerImgURL = UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerImgURL
    val musicPlayerHDImgURL = UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerHDImgURL
    val musicPlayerTitle = UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerTitle
    val unreleasedLyricsForPlayer = UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerLyrics
    val rememberMusicPlayerDescription =
        UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescription
    val rememberMusicPlayerDescriptionBy =
        UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescriptionBy
    val rememberMusicPlayerArtworkBy =
        UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerArtworkBy
    val rememberMusicPlayerDescriptionOrigin =
        UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescriptionOrigin
    val audioUrl = UnreleasedViewModel.UnreleasedUtils.musicAudioURL
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
            modifier = Modifier
                .background(md_theme_dark_onTertiary)
                .padding(bottom = 30.dp),
            contentPadding = contentPadding,
        ) {

            items(headerData) { data ->
                ArtBoard(data.artwork)
            }

            items(unreleasedData.value) { data ->
                SongThing1(
                    songName = data.songName, specificArtwork = data.imgURL,
                    onClick =  {
                        musicPlayerImgURL.value = data.imgURL
                        musicPlayerTitle.value = data.songName
                        unreleasedLyricsForPlayer.value = data.lyrics
                        rememberMusicPlayerDescription.value = data.songDescription
                        rememberMusicPlayerDescriptionBy.value = data.descriptionBy
                        rememberMusicPlayerDescriptionOrigin.value = data.descriptionOrigin
                        rememberMusicPlayerArtworkBy.value = data.specificArtworkBy
                        audioUrl.value = data.audioLink
                        musicPlayerHDImgURL.value = data.imgURLHD
                        itemOnClick()
                    }
                    )
            }
            items(footerData) { data ->
                if (UnreleasedViewModel.UnreleasedUtils.musicPlayerActivate.value) {
                    GIFThing(
                        imgURL = data.footerImg,
                        modifier = Modifier
                            .background(md_theme_dark_surface)
                            .padding(bottom = 150.dp)
                            .fillMaxWidth()
                            .height(70.dp)
                    )
                } else {
                    GIFThing(
                        imgURL = data.footerImg,
                        modifier = Modifier
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUnreleasedScreen() {
    val systemUIController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ) { false }
    LaunchedEffect(key1 = coroutineScope) {
        coroutineScope.launch {
            systemUIController.setStatusBarColor(color = md_theme_dark_onTertiary)
        }
    }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val musicControlBoolean =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControlImg }
    val currentControlIcon = rememberSaveable { mutableListOf(0, 1) }
    val currentAudioURL = UnreleasedViewModel.UnreleasedUtils.musicAudioURL
    rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescriptionBy }
    rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescriptionOrigin }
    UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerArtworkBy
    val currentGIFURL =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentLoadingStatusGIFURL }
    val currentSongMaxDuration =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentSongMaxDuration }
    val currentSongCurrentDuration =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentSongCurrentDuration }
    rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentSongIsPlaying }
    if (musicControlBoolean.value) {
        val playIcon = rememberMusicPlayerControlImg[0]  //play icon
        currentControlIcon[0] = playIcon
    } else {
        val pauseIcon = rememberMusicPlayerControlImg[1] //pause icon
        currentControlIcon[0] = pauseIcon
    }
    if (UnreleasedViewModel.UnreleasedUtils.isDataLoaded.value) {
        UnreleasedScreen {
            UnreleasedViewModel.UnreleasedUtils.musicPlayerActivate.value = true
            UnreleasedViewModel.UnreleasedUtils.musicCompleted.value = false
            UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControl.value = false
            UnreleasedViewModel.UnreleasedUtils.currentSongIsPlaying.value = false
            currentGIFURL.value =
                UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerLoadingGIF.component1()[0].gifURL
            UnreleasedViewModel.UnreleasedUtils.musicPlayerVisibility.value = false
            if (Build.VERSION.SDK_INT <= 26) {
                UnreleasedViewModel.UnreleasedUtils.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            } else {
                UnreleasedViewModel.UnreleasedUtils.mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build()
                )
            }
            if (UnreleasedViewModel.UnreleasedUtils.mediaPlayer.isPlaying || !UnreleasedViewModel.UnreleasedUtils.mediaPlayer.isPlaying) {
                UnreleasedViewModel.UnreleasedUtils.mediaPlayer.stop()
                UnreleasedViewModel.UnreleasedUtils.mediaPlayer.reset().also {
                    UnreleasedViewModel.UnreleasedUtils.mediaPlayer.setDataSource(currentAudioURL.value)
                    UnreleasedViewModel.UnreleasedUtils.mediaPlayer.prepareAsync()
                    UnreleasedViewModel.UnreleasedUtils.mediaPlayer.setOnPreparedListener {
                        try {
                            it.start()
                            currentSongMaxDuration.value = it.duration
                            currentSongCurrentDuration.value = it.currentPosition
                        } catch (e: Exception) {
                            currentGIFURL.value = Constants.MUSIC_ERROR_GIF
                        }
                        if (it.isPlaying) {
                            currentGIFURL.value =
                                UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerPlayingGIF.component1()[0].gifURL
                            UnreleasedViewModel.UnreleasedUtils.musicPlayerVisibility.value = true
                            UnreleasedViewModel.UnreleasedUtils.currentSongIsPlaying.value = true
                        }
                        it.setOnCompletionListener {
                            UnreleasedViewModel.UnreleasedUtils.musicCompleted.value = true
                            UnreleasedViewModel.UnreleasedUtils.musicPlayerVisibility.value = false
                            UnreleasedViewModel.UnreleasedUtils.currentLoadingStatusGIFURL.value =
                                Constants.MUSIC_ERROR_GIF
                            UnreleasedViewModel.UnreleasedUtils.currentSongIsPlaying.value = false
                        }
                    }
                }
            }
        }
    } else {
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
                modifier = Modifier
                    .background(md_theme_dark_onTertiary)
                    .padding(bottom = 30.dp),
                contentPadding = contentPadding,
            ) {
                item {
                    ArtBoard(
                        imgURL = "data.artwork",
                        imgModifier = Modifier
                            .size(150.dp)
                            .shadow(2.dp)
                            .shimmer(true)
                    )
                }
                items(8) {
                    SongThing1(
                        songName = "Broken Satellites",
                        specificArtwork = "data.imgURL",
                        onClick = {},
                        imgModifier = Modifier
                            .padding(top = 10.dp)
                            .requiredHeight(50.dp)
                            .padding(start = 10.dp)
                            .requiredWidth(50.dp)
                            .shimmer(true),
                        titleModifier = Modifier.shimmer(true),
                        lyricsModifier = Modifier
                            .padding(top = 3.dp, bottom = 3.dp)
                            .background(color = md_theme_light_outline)
                            .wrapContentSize()
                            .padding(2.dp)
                            .shimmer(true)
                    )
                }
            }
        }
    }
}