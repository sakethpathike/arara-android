package com.sakethh.arara

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.api.Caching.unreleasedCache
import com.sakethh.arara.api.isInternetAvailable
import com.sakethh.arara.bookmarks.BookMarkRepo
import com.sakethh.arara.home.settings.readInAppBrowserSetting
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.UnreleasedViewModel.UnreleasedUtils.mediaPlayer
import com.sakethh.arara.unreleased.bottomMusicPlayer.BottomMusicPlayerUI
import com.sakethh.arara.unreleased.currentMusicScreen.CurrentMusicScreenViewModel
import com.sakethh.arara.unreleased.currentMusicScreen.CurrentMusicScreenViewModel.CurrentMusicScreenUtils.isBtmSheetCollapsed
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity() : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = createDataStore("settingsPreferences")
        lifecycleScope.launchWhenCreated {
            window.setBackgroundDrawableResource(android.R.color.transparent)
            readInAppBrowserSetting(dataStore)
            BottomNavigationBar.isBottomBarHidden.value = false
        }
        setContent {
            val bottomSheetState = rememberBottomSheetState(
                initialValue = BottomSheetValue.Collapsed,
                animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
            )
            val systemUIController = rememberSystemUiController()
            val scaffoldState =
                rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
            systemUIController.setNavigationBarColor(md_theme_dark_primaryContainer)
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val paddingValue = 20.dp
            val currentUnreleasedViewModel:CurrentMusicScreenViewModel = viewModel()
            MaterialTheme(typography = Typography) {
                val animatedNavController = rememberAnimatedNavController()
                val sharedViewModel: SharedViewModel = viewModel()
                Scaffold(
                    bottomBar = {
                        if (!BottomNavigationBar.isBottomBarHidden.value) {
                            BottomNavigationBar(
                                navController = animatedNavController, items = listOf(
                                    BottomNavigationItem(
                                        name = "Home",
                                        route = "homeScreen",
                                        selectedIcon = R.drawable.home_filled,
                                        nonSelectedIcon = R.drawable.home
                                    ),
                                    BottomNavigationItem(
                                        name = "Unreleased",
                                        route = "unreleased",
                                        selectedIcon = R.drawable.unreleased_icon_filled,
                                        nonSelectedIcon = R.drawable.unreleased_icon
                                    ),
                                    BottomNavigationItem(
                                        name = "Bookmarks",
                                        route = "bookmarks",
                                        selectedIcon = R.drawable.bookmarks_filled,
                                        nonSelectedIcon = R.drawable.bookmarks
                                    )
                                )
                            )
                        }
                    }
                ) {
                    val isBtmVisible = sharedViewModel.isBottomNavVisible
                    val btmPaddingForMusicPlayer = if (isBtmVisible.value) {
                        100.dp
                    } else {
                        20.dp
                    }
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        mediaPlayer.stop()
                        mediaPlayer.reset()
isBtmSheetCollapsed.value=true
                    }
                    BottomSheetScaffold(
                        backgroundColor = Color.Transparent,
                        sheetBackgroundColor =Color.Transparent,
                        drawerBackgroundColor = Color.Transparent,
                        drawerScrimColor = Color.Transparent,
                        sheetPeekHeight = 0.dp,
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            if (isInternetAvailable(context = context)) {
                                BottomMusicPlayerUI(
                                    modifier = Modifier
                                        .padding(
                                            start = paddingValue,
                                            end = paddingValue,
                                            bottom = btmPaddingForMusicPlayer
                                        )
                                        .requiredHeight(65.dp)
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(5.dp),
                                            color = md_theme_dark_secondary
                                        )
                                        .background(color = (md_theme_dark_onSecondary))
                                        .clickable { coroutineScope.launch { scaffoldState.bottomSheetState.expand() } },
                                    animatedNavController = animatedNavController,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }) {
                        Navigation(
                            navController = animatedNavController,
                            sharedViewModel = sharedViewModel,
                            dataStoreForSettingsScreen = dataStore,
                            bottomSheetScaffoldState = scaffoldState
                        )
                    }
                }
            }
        }
        unreleasedCache(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BookMarkRepo().realm.close()
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MaterialTheme(typography = Typography) {

    }
}

