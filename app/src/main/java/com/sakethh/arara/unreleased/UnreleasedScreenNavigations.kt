package com.sakethh.arara.unreleased

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen

@Composable
fun UnreleasedNavigation(unreleasedMusicPlayerOnClick:()->Unit, currentSongTitleForCurrentMusicScreen:String, currentMusicImgForCurrentMusicScreen:String, currentSongLyricsForCurrentMusicScreen:String) {
    val navController = rememberNavController()
    val unreleasedViewModel:UnreleasedViewModel= viewModel()
    NavHost(navController = navController, startDestination = "unreleasedScreen") {
        unreleasedViewModel.rememberNavController.value = navController
        composable("unreleasedScreen") {
            UnreleasedScreen(musicPlayerOnClick = unreleasedMusicPlayerOnClick)
        }
        composable("unreleasedCurrentMusicScreen") {
            UnreleasedCurrentMusicScreen(
                currentSongTitleForCurrentMusicScreen,
                currentMusicImgForCurrentMusicScreen,
                currentSongLyricsForCurrentMusicScreen
            )
        }
    }
}