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
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavController() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "unreleasedMusicScreen") {
        composable("unreleasedMusicScreen", popEnterTransition = {
            slideInVertically(initialOffsetY = { -1000 }) + fadeIn(
                tween(100)
            )
        }) {
            MainScreen(navHostController = navController)
        }
        composable(route = "currentPlayingUnreleasedMusicScreen/{songName}/{lyrics}/{imgURL}",
            arguments = listOf(
                navArgument("songName") { type = NavType.StringType },
                navArgument("imgURL") { type = NavType.StringType },
                navArgument("lyrics") { type = NavType.StringType }),
            enterTransition = {
                slideInVertically(initialOffsetY = { 100000 }) + fadeIn(tween(100))
            }) {
            UnreleasedCurrentMusicScreen(
                songName = it.arguments?.getString("songName")!!,
                imgURL = it.arguments?.getString("imgURL")!!,
                lyrics = it.arguments?.getString("lyrics")!!
            )
        }
    }
}
