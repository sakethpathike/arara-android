package com.sakethh.arara

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.home.HomeScreen
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.ui.theme.md_theme_dark_onPrimary
import com.sakethh.arara.ui.theme.md_theme_dark_onTertiary
import com.sakethh.arara.unreleased.*
import com.sakethh.arara.unreleased.UnreleasedCache.unreleasedCache
import com.sakethh.arara.unreleased.UnreleasedViewModel.MediaPlayer.mediaPlayer
import com.sakethh.arara.unreleased.bottomMusicPlayer.BottomMusicPlayerUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity() : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated{
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setContent {
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
               HomeScreen()
            /*NavController()*/
            }
        }
        /*unreleasedCache(this)*/
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUnreleasedScreen(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val systemUIController=rememberSystemUiController()
    val coroutineScope= rememberCoroutineScope()
     LaunchedEffect(key1 = coroutineScope){
         coroutineScope.launch {
             systemUIController.setStatusBarColor(color = md_theme_dark_onTertiary)
         }
     }
    val context = LocalContext.current
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedSongNameForPlayer = rememberSaveable { unreleasedViewModel.rememberMusicPlayerTitle }
    val unreleasedImgURLForPlayer = rememberSaveable { unreleasedViewModel.rememberMusicPlayerImgURL }
    val unreleasedLyricsForPlayer = rememberSaveable { unreleasedViewModel.rememberMusicPlayerLyrics }
    val musicControlBoolean = rememberSaveable { unreleasedViewModel.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        rememberSaveable { unreleasedViewModel.rememberMusicPlayerControlImg }
    val musicPlayerActivate = rememberSaveable { unreleasedViewModel.musicPlayerActivate }
    val currentControlIcon = rememberSaveable { mutableListOf(0, 1) }
    val currentAudioURL = unreleasedViewModel.musicAudioURL
    val rememberMusicPlayerDescription =
        rememberSaveable { unreleasedViewModel.rememberMusicPlayerDescription }
    val rememberMusicPlayerDescriptionBy =
        rememberSaveable { unreleasedViewModel.rememberMusicPlayerDescriptionBy }
    val rememberMusicPlayerDescriptionOrigin =
        rememberSaveable { unreleasedViewModel.rememberMusicPlayerDescriptionOrigin }
    val rememberMusicPlayerArtworkBy = unreleasedViewModel.rememberMusicPlayerArtworkBy
    val currentGIFURL = rememberSaveable { unreleasedViewModel.currentLoadingStatusGIFURL }
    val currentSongMaxDuration = rememberSaveable { unreleasedViewModel.currentSongMaxDuration }
    val currentSongCurrentDuration= rememberSaveable{unreleasedViewModel.currentSongCurrentDuration}
    val currentSongIsPlaying = rememberSaveable { unreleasedViewModel.currentSongIsPlaying }
    if (musicControlBoolean.value) {
        val playIcon = rememberMusicPlayerControlImg[0]  //play icon
        currentControlIcon[0] = playIcon
    } else {
        val pauseIcon = rememberMusicPlayerControlImg[1] //pause icon
        currentControlIcon[0] = pauseIcon
    }
    Scaffold(floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (!isInternetAvailable(context = context)) {
                CustomBottomSnackBar(image = randomLostInternetImg())
            }
            if (musicPlayerActivate.value && !UnreleasedViewModel.MediaPlayer.musicCompleted.value) {
                if (isInternetAvailable(context = context)) {
                     BottomMusicPlayerUI(
                        songName = unreleasedSongNameForPlayer.value,
                        imgUrl = unreleasedImgURLForPlayer.value,
                        onClick = {
                            val dataForCurrentMusicScreen = UnreleasedScreenCurrentData(
                                currentSongName = unreleasedSongNameForPlayer.value,
                                currentImgUrl = unreleasedViewModel.rememberMusicPlayerHDImgURL.value,
                                currentLyrics = unreleasedLyricsForPlayer.value,
                                songDescription = rememberMusicPlayerDescription.value,
                                descriptionBy = rememberMusicPlayerDescriptionBy.value,
                                descriptionOrigin = rememberMusicPlayerDescriptionOrigin.value,
                                artworkBy = rememberMusicPlayerArtworkBy.value,
                                currentSongMaxDuration = unreleasedViewModel.musicDuration(currentSongMaxDuration.value.toLong()).value,
                                currentDuration = unreleasedViewModel.musicDuration(currentSongCurrentDuration.value.toLong()).value,
                                currentDurationFloat = currentSongCurrentDuration.value.toFloat(),
                                isPlaying = currentSongIsPlaying.value
                            )
                            sharedViewModel.data(data = dataForCurrentMusicScreen)
                            navHostController.navigate("currentPlayingUnreleasedMusicScreen")

                        },
                        onControlClick = {
                            if (mediaPlayer.isPlaying) {
                                mediaPlayer.pause()
                            } else {
                                mediaPlayer.start()
                            }
                        },
                        onControlClickImg = currentControlIcon[0]
                    )
                }
            }
        }
    ) {
        UnreleasedScreen {
            unreleasedViewModel.musicPlayerActivate.value = true
            UnreleasedViewModel.MediaPlayer.musicCompleted.value = false
            unreleasedViewModel.rememberMusicPlayerControl.value = false
            unreleasedViewModel.currentSongIsPlaying.value=false
            currentGIFURL.value = unreleasedViewModel.rememberMusicPlayerLoadingGIF.component1()[0].gifURL
            unreleasedViewModel.musicPlayerVisibility.value = false
            if (Build.VERSION.SDK_INT <= 26) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            } else {
                mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build()
                )
            }
            if (mediaPlayer.isPlaying || !mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset().also {
                    mediaPlayer.setDataSource(currentAudioURL.value)
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener {
                        try {
                            it.start()
                            currentSongMaxDuration.value = it.duration
                            currentSongCurrentDuration.value=it.currentPosition
                        } catch (e: Exception) {
                            currentGIFURL.value =
                                Constants.MUSIC_ERROR_GIF
                        }
                        if (it.isPlaying) {
                            currentGIFURL.value = unreleasedViewModel.rememberMusicPlayerPlayingGIF.component1()[0].gifURL
                            unreleasedViewModel.musicPlayerVisibility.value = true
                            unreleasedViewModel.currentSongIsPlaying.value=true
                        }
                        it.setOnCompletionListener {
                            UnreleasedViewModel.MediaPlayer.musicCompleted.value = true
                            unreleasedViewModel.musicPlayerVisibility.value = false
                            unreleasedViewModel.currentLoadingStatusGIFURL.value =
                                Constants.MUSIC_ERROR_GIF
                            unreleasedViewModel.currentSongIsPlaying.value=false
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MaterialTheme(typography = Typography) {

    }
}