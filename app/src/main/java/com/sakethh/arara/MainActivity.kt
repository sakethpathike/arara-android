package com.sakethh.arara

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
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
import com.sakethh.arara.unreleased.musicPlayer.MusicPlayerViewModel

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
fun MainScreen(navHostController: NavHostController,sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedSongNameForPlayer = remember { unreleasedViewModel.rememberMusicPlayerTitle }
    val unreleasedImgURLForPlayer = remember { unreleasedViewModel.rememberMusicPlayerImgURL }
    val unreleasedLyricsForPlayer = remember { unreleasedViewModel.rememberMusicPlayerLyrics }
    val musicControlBoolean = remember { unreleasedViewModel.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        remember { unreleasedViewModel.rememberMusicPlayerControlImg }
    val musicPlayerActivate = remember { unreleasedViewModel.musicPlayerActivate }
    val currentControlIcon = remember { mutableListOf(0, 1) }
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
                            val dataForCurrentMusicScreen= UnreleasedScreenCurrentData(
                                currentSongName = unreleasedSongNameForPlayer.value,
                                currentImgUrl = unreleasedImgURLForPlayer.value,
                                currentLyrics = unreleasedLyricsForPlayer.value
                            )
                            sharedViewModel.data(data = dataForCurrentMusicScreen)
                            navHostController.navigate("currentPlayingUnreleasedMusicScreen")
                        },
                        onControlClick = {
                            if (!musicControlBoolean.value) { //pause music if value is false

                            } else { //play music if value is true

                            }
                        },
                        onControlClickImg = currentControlIcon[0]
                    )
                }
            }
        }
    ) {
        UnreleasedScreen(itemOnClick = {
            unreleasedViewModel.musicPlayerActivate.value = true
            unreleasedViewModel.rememberMusicPlayerControl.value = false
        })
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MaterialTheme(typography = Typography) {

    }
}