package com.sakethh.arara

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.unreleased.*
import com.sakethh.arara.unreleased.UnreleasedCache.unreleasedCache
import com.sakethh.arara.unreleased.musicPlayer.MusicPlayerUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity() : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
                NavController()
            }
        }
        unreleasedCache(this)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val mediaPlayer =UnreleasedViewModel.MediaPlayer.mediaPlayer
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedSongNameForPlayer = remember { unreleasedViewModel.rememberMusicPlayerTitle }
    val unreleasedImgURLForPlayer = remember { unreleasedViewModel.rememberMusicPlayerImgURL }
    val unreleasedLyricsForPlayer = remember { unreleasedViewModel.rememberMusicPlayerLyrics }
    val musicControlBoolean = remember { unreleasedViewModel.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        remember { unreleasedViewModel.rememberMusicPlayerControlImg }
    val musicPlayerActivate = remember { unreleasedViewModel.musicPlayerActivate }
    val currentControlIcon = remember { mutableListOf(0, 1) }
    val currentAudioURL = unreleasedViewModel.musicAudioURL
    val rememberMusicPlayerDescription =
        remember { unreleasedViewModel.rememberMusicPlayerDescription }
    val rememberMusicPlayerDescriptionBy =
        remember { unreleasedViewModel.rememberMusicPlayerDescriptionBy }
    val rememberMusicPlayerDescriptionOrigin =
        remember { unreleasedViewModel.rememberMusicPlayerDescriptionOrigin }
    val rememberMusicPlayerArtworkBy = unreleasedViewModel.rememberMusicPlayerArtworkBy

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
            if (musicPlayerActivate.value) {
                if (isInternetAvailable(context = context)) {
                    MusicPlayerUI(
                        songName = unreleasedSongNameForPlayer.value,
                        imgUrl = unreleasedImgURLForPlayer.value,
                        onClick = {
                            val dataForCurrentMusicScreen = UnreleasedScreenCurrentData(
                                currentSongName = unreleasedSongNameForPlayer.value,
                                currentImgUrl = unreleasedImgURLForPlayer.value,
                                currentLyrics = unreleasedLyricsForPlayer.value,
                                songDescription = rememberMusicPlayerDescription.value,
                                descriptionBy = rememberMusicPlayerDescriptionBy.value,
                                descriptionOrigin = rememberMusicPlayerDescriptionOrigin.value,
                                artworkBy = rememberMusicPlayerArtworkBy.value
                            )
                            sharedViewModel.data(data = dataForCurrentMusicScreen)
                            navHostController.navigate("currentPlayingUnreleasedMusicScreen")

                        },
                        onControlClick = {
                            if (!musicControlBoolean.value) { //pause music if value is false
                                if (mediaPlayer.isPlaying) {
                                    mediaPlayer.pause()
                                }
                            } else { //play music if value is true
                                if (!mediaPlayer.isPlaying) {
                                    mediaPlayer.setOnPreparedListener {
                                        it.start()
                                    }
                                }
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
            unreleasedViewModel.rememberMusicPlayerControl.value = false

            if (Build.VERSION.SDK_INT <= 26) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            } else {
                mediaPlayer.setAudioAttributes(
                    AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build()
                )
            }
            if (mediaPlayer.isPlaying || !mediaPlayer.isPlaying) {
               Log.d("ARARA LOG","Before Resting")
                mediaPlayer.stop()
                mediaPlayer.reset().also {
                    Log.d("ARARA LOG","After Resting")
                    mediaPlayer.setDataSource(currentAudioURL.value)
                    mediaPlayer.prepareAsync()
                    mediaPlayer.setOnPreparedListener {
                        it.start()
                    }
                }
            }else{
                Toast.makeText(context,"Else Is Triggered",Toast.LENGTH_SHORT).show()
                /*mediaPlayer.setDataSource(currentAudioURL.value)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {
                    it.start()
                }*/
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