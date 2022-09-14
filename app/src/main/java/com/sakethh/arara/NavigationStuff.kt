package com.sakethh.arara

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sakethh.arara.unreleased.SharedViewModel
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavController() {
    val navController = rememberAnimatedNavController()
    val sharedViewModel:SharedViewModel= viewModel()
    AnimatedNavHost(navController = navController, startDestination = "unreleasedMusicScreen") {
        composable("unreleasedMusicScreen", popEnterTransition = {
            slideInVertically(initialOffsetY = { -1000 }) + fadeIn(
                tween(100)
            )
        }) {
            MainScreen(navHostController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = "currentPlayingUnreleasedMusicScreen",
            enterTransition = {
                slideInVertically(initialOffsetY = { 100000 }) + fadeIn(tween(100))
            }) {
            UnreleasedCurrentMusicScreen(
                sharedViewModel=sharedViewModel
            )
        }
    }
}
