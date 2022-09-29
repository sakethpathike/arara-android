package com.sakethh.arara

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sakethh.arara.unreleased.SharedViewModel
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavController() {
    val navController = rememberAnimatedNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    AnimatedNavHost(navController = navController, startDestination = "unreleasedMusicScreen") {
        composable("unreleasedMusicScreen") {
            MainUnreleasedScreen(navHostController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = "currentPlayingUnreleasedMusicScreen",
            enterTransition = {
                slideInHorizontally { 1000 }+ fadeIn(tween(300))
            }, exitTransition = {slideOutHorizontally { 1000 }+ fadeOut(tween(300)) }){
            UnreleasedCurrentMusicScreen(
                navHostController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}
