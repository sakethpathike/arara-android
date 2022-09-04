package com.sakethh.arara.unreleased

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen

@Composable
fun UnreleasedNavigation() {
    val navController = rememberNavController()
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val musicControlBoolean = unreleasedViewModel.rememberMusicPlayerControl
    val musicPlayerActivate = unreleasedViewModel.musicPlayerActivate
    NavHost(navController = navController, startDestination = "") {
        composable("unreleasedScreen") {
            UnreleasedScreen(musicPlayerOnClick = {
                musicPlayerActivate.value = true; musicControlBoolean.value = false
            })
        }
        composable("unreleasedCurrentMusicScreen"){
             UnreleasedCurrentMusicScreen("","","")
        }
    }
}