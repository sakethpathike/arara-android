package com.sakethh.arara

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.unreleased.UnreleasedCache.unreleasedCache
import com.sakethh.arara.unreleased.UnreleasedScreen
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.isInternetAvailable

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/){
             DestinationsNavHost(navGraph = NavGraphs.root)
         }
        }
        unreleasedCache(this)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator){
    val context = LocalContext.current
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedSongNameForPlayer = unreleasedViewModel.rememberMusicPlayerTitle
    val unreleasedImgURLForPlayer = unreleasedViewModel.rememberMusicPlayerImgURL
    val musicControlBoolean = unreleasedViewModel.rememberMusicPlayerControl
    val rememberMusicPlayerControlImg = unreleasedViewModel.rememberMusicPlayerControlImg
    val musicPlayerActivate = unreleasedViewModel.musicPlayerActivate
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
                            onClick = { musicPlayerOnClick(navigator = navigator,"Title","","Lyrics")},
                            onControlClick = {
                                if (!musicControlBoolean.value) { //pause music if value is false
                                    Toast.makeText(
                                        context,
                                        "--Paused--",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else { //play music if value is true
                                    Toast.makeText(
                                        context,
                                        "--Playing--",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            onControlClickImg = currentControlIcon[0]
                        )
                    }
                }
            }
        ) {
          UnreleasedScreen(itemOnClick = {
              unreleasedViewModel.musicPlayerActivate.value=true
              unreleasedViewModel.rememberMusicPlayerControl.value=false})
        }
}
@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MaterialTheme(typography = Typography) {

    }
}