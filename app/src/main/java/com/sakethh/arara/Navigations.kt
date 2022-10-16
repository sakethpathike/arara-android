package com.sakethh.arara

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sakethh.arara.bookmarks.BookMarkScreen
import com.sakethh.arara.home.HomeScreen
import com.sakethh.arara.ui.theme.md_theme_dark_onSecondary
import com.sakethh.arara.ui.theme.md_theme_dark_onSecondaryContainer
import com.sakethh.arara.ui.theme.md_theme_dark_primaryContainer
import com.sakethh.arara.unreleased.MainUnreleasedScreen
import com.sakethh.arara.unreleased.SharedViewModel
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen

data class BottomNavigationItem(
    val name: String, val route: String, val selectedIcon: Int, val nonSelectedIcon: Int
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController,sharedViewModel: SharedViewModel) {
    AnimatedNavHost(navController = navController, startDestination = "homeScreen") {
        composable(route = "homeScreen", exitTransition = { ExitTransition.None }) {
            HomeScreen()
        }
        composable(route = "bookmarks", exitTransition = { ExitTransition.None }) {
            BookMarkScreen()
        }
        composable(route = "unreleased", exitTransition = { ExitTransition.None }) {
            MainUnreleasedScreen()
        }
        composable(route = "currentPlayingUnreleasedMusicScreen",
            enterTransition = {
                slideInHorizontally { 1000 } + fadeIn(tween(300))
            }, exitTransition = { slideOutHorizontally { 1000 } + fadeOut(tween(300)) }) {
            UnreleasedCurrentMusicScreen(
                navHostController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavigationItem>
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val icon = mutableListOf(0)
    NavigationBar(
        containerColor = md_theme_dark_primaryContainer,
        contentColor = md_theme_dark_onSecondary
    ) {
        items.forEach { item ->
            val selected = backStackEntry.value?.destination?.route == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                icon = {
                    if (selected) {
                        icon[0] = item.selectedIcon
                    } else {
                        icon[0] = item.nonSelectedIcon
                    }
                    Icon(
                        painter = painterResource(id = icon[0]),
                        contentDescription = item.route,
                        modifier = Modifier.size(24.dp)
                    )
                    Icon(
                        painter = painterResource(id = icon[0]),
                        contentDescription = item.route,
                        modifier = Modifier.size(24.dp)
                    )

                }, label = {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = md_theme_dark_onSecondaryContainer,
                    unselectedTextColor = md_theme_dark_onSecondaryContainer,
                    unselectedIconColor = md_theme_dark_onSecondaryContainer
                )
            )
        }
    }
}