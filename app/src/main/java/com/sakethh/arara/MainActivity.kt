package com.sakethh.arara

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.bookmarks.BookMarkRepo
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.*
import com.sakethh.arara.unreleased.UnreleasedCache.unreleasedCache
import com.sakethh.arara.unreleased.bottomMusicPlayer.BottomMusicPlayerUI
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setContent {
            val systemUIController = rememberSystemUiController()
            systemUIController.setNavigationBarColor(md_theme_dark_primaryContainer)
            val animatedNavController = rememberAnimatedNavController()
            val sharedViewModel:SharedViewModel= viewModel()
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
                Scaffold(
                    floatingActionButton = {
                        if (isInternetAvailable(context = this)) {
                            if(UnreleasedViewModel.MediaPlayer.musicPlayerActivate.value && !UnreleasedViewModel.MediaPlayer.musicCompleted.value){
                                BottomMusicPlayerUI(
                                    animatedNavController = animatedNavController,
                                    sharedViewModel = sharedViewModel
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    bottomBar = {
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
                ) {
                    Navigation(navController = animatedNavController, sharedViewModel = sharedViewModel)
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