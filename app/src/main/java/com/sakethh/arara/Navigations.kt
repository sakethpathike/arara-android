package com.sakethh.arara

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sakethh.arara.BottomNavigationBar.isBottomBarHidden
import com.sakethh.arara.bookmarks.BookMarkScreen
import com.sakethh.arara.home.HomeScreen
import com.sakethh.arara.home.settings.SettingsScreen
import com.sakethh.arara.home.subHomeScreen.SubHomeScreen
import com.sakethh.arara.ui.theme.md_theme_dark_onSecondary
import com.sakethh.arara.ui.theme.md_theme_dark_onSecondaryContainer
import com.sakethh.arara.ui.theme.md_theme_dark_primaryContainer
import com.sakethh.arara.unreleased.MainUnreleasedScreen
import com.sakethh.arara.unreleased.currentMusicScreen.UnreleasedCurrentMusicScreen

data class BottomNavigationItem(
    val name: String, val route: String, val selectedIcon: Int, val nonSelectedIcon: Int
)

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    dataStoreForSettingsScreen: DataStore<Preferences>,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    AnimatedNavHost(navController = navController, startDestination = "homeScreen") {
        composable(route = "homeScreen", exitTransition = { ExitTransition.None }) {
            HomeScreen(navController = navController, sharedViewModel = sharedViewModel,bottomSheetScaffoldState=bottomSheetScaffoldState)
        }
        composable(route = "subHomeScreen",
            enterTransition = {
                slideInHorizontally { 1000 } + fadeIn(tween(300))
            }, exitTransition = { slideOutHorizontally { 1000 } + fadeOut(tween(300)) }) {
            SubHomeScreen(navController = navController, sharedViewModel = sharedViewModel,bottomSheetScaffoldState=bottomSheetScaffoldState)
        }
        composable(route = "bookmarks", exitTransition = { ExitTransition.None }) {
            BookMarkScreen(navController = navController, sharedViewModel = sharedViewModel,bottomSheetScaffoldState=bottomSheetScaffoldState)
        }
        composable(route = "unreleased", exitTransition = { ExitTransition.None }) {
            MainUnreleasedScreen(navController = navController,bottomSheetScaffoldState=bottomSheetScaffoldState,
                sharedViewModel = sharedViewModel)
        }
        composable(route = "settings", exitTransition = { ExitTransition.None }) {
            SettingsScreen(
                dataStore = dataStoreForSettingsScreen,
                sharedViewModel = sharedViewModel,
                navController = navController,bottomSheetScaffoldState=bottomSheetScaffoldState
            )
        }
        composable(route = "webView", exitTransition = { ExitTransition.None }) {
            WebView(sharedViewModel = sharedViewModel, navController = navController)
        }
        composable(route = "currentPlayingUnreleasedMusicScreen",
            enterTransition = {
                slideInHorizontally { 1000 } + fadeIn(tween(300))
            }, exitTransition = { slideOutHorizontally { 1000 } + fadeOut(tween(300)) }) {
            UnreleasedCurrentMusicScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                scaffoldState = bottomSheetScaffoldState
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
    when (backStackEntry.value?.destination?.route) {
        "currentPlayingUnreleasedMusicScreen" -> {
            isBottomBarHidden.value = true
        }
        "webView" -> {
            isBottomBarHidden.value = true
        }
        "settings" -> {
            isBottomBarHidden.value = true
        }
        "subHomeScreen" -> {
            isBottomBarHidden.value = true
        }
        else -> {
            isBottomBarHidden.value = false
        }
    }
    NavigationBar(
        containerColor = md_theme_dark_primaryContainer,
        contentColor = md_theme_dark_onSecondary
    ) {
        items.forEach { item ->
            val selected = backStackEntry.value?.destination?.route == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if(backStackEntry.value?.destination?.route != item.route){
                        navController.navigate(item.route)
                    }
                },
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

object BottomNavigationBar {
    val isBottomBarHidden = mutableStateOf(false)
}