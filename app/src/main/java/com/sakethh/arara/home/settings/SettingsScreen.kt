package com.sakethh.arara.home.settings

import android.content.ClipDescription
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sakethh.arara.*
import com.sakethh.arara.R
import com.sakethh.arara.api.Caching
import com.sakethh.arara.bookmarks.BookMarkRepo
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.UnreleasedViewModel
import io.realm.kotlin.ext.query
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    dataStore: DataStore<Preferences>,
    sharedViewModel: SharedViewModel,
    navController: NavController,
    bookMarkRepo: BookMarkRepo = BookMarkRepo(),
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    BackHandler {
        BottomNavigationBar.isBottomBarHidden.value = false
        navController.navigate("homeScreen") {
            popUpTo(0)
        }
    }
    sharedViewModel.isBottomNavVisible.value=false
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val bottomPaddingForGIF = if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
        80.dp
    } else {
        0.dp
    }
    MaterialTheme(typography = Typography) {
        LazyColumn(
            modifier = Modifier
                .background(md_theme_dark_surface)
                .fillMaxSize()
                .animateContentSize()
        ) {
            item {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate("homeScreen")
                            }, modifier = Modifier
                                .padding(start = 10.dp)
                                .size(30.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.arrow_right),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .rotate(180f)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 24.sp,
                            color = md_theme_dark_onSurface
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface),
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.requiredHeight(45.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Open web links in-app",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            color = md_theme_dark_onSurface,
                            modifier = Modifier.padding(start = 15.dp, top = 0.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.Center) {
                        Switch(checked = Settings.inAppBrowserSetting.value,
                            modifier = Modifier.padding(end = 20.dp),
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = md_theme_dark_onSecondaryContainer,
                                checkedThumbColor = md_theme_dark_onPrimary
                            ),
                            onCheckedChange = {
                                coroutineScope.launch {
                                    changeInAppBrowserSetting(
                                        isInAppBrowsing = !Settings.inAppBrowserSetting.value,
                                        dataStore = dataStore
                                    )
                                    readInAppBrowserSetting(dataStore = dataStore)
                                }
                            },
                            thumbContent = {
                                if (Settings.inAppBrowserSetting.value) {
                                    Image(
                                        painter = painterResource(id = R.drawable.circle_checked),
                                        contentDescription = "close icon",
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            })
                    }
                }
            }
            item {
                DividerComposable()
            }
            item {
                Text(
                    text = "Credits",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = md_theme_dark_onSurface,
                    modifier = Modifier.titlePadding()
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.redirectToWeb(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        webURL = "https://www.reddit.com//r/auroramusic/"
                    ),
                    title = "Subreddit of uncle arara",
                    description = "Most of the data is collected from r/auroramusic"
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.redirectToWeb(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        webURL = "https://github.com/sakethpathike/arara-server_side"
                    ),
                    title = "API",
                    description = "Data from r/auroramusic is fetched by an API which returns data from subreddit"
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.redirectToWeb(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        webURL = "https://tabler-icons.io/"
                    ),
                    title = "Icons",
                    description = "Every icon used in this project is stolen from tabler-icons.io"
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.redirectToWeb(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        webURL = "https://2fpvxo3g.bearblog.dev/gifs-for-arara-android-client/"
                    ),
                    title = "Gifs",
                    description = "Every Gif used in this project is stolen from lottiefiles.com"
                )
            }
            item {
                DividerComposable()
            }
            item {
                Text(
                    text = "Storage",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = md_theme_dark_onSurface,
                    modifier = Modifier.titlePadding()
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            context.cacheDir.deleteRecursively()
                        }.also {
                            Toast.makeText(context, "Cleared cache(s)", Toast.LENGTH_SHORT).show()
                        }
                    },
                    title = "Clear cache",
                    description = "You can free up storage by clearing your cache. Your bookmarks won't be removed"
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            bookMarkRepo.realm.write {
                                val bookMarksData = this.query<BookMarksDB>().find()
                                this.delete(bookMarksData)
                            }
                        }.also {
                            Toast.makeText(
                                context,
                                "Cleared Bookmarks from local database",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    title = "Clear Bookmarks",
                    description = "Remove all bookmarks from local database. Your cache won't be removed."
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            val cache = async { context.cacheDir.deleteRecursively()}
                            val bookMarks = async {
                                bookMarkRepo.realm.write {
                                    val bookMarksData = this.query<BookMarksDB>().find()
                                    this.delete(bookMarksData)
                                }
                            }
                            cache.await()
                            bookMarks.await()
                            Toast.makeText(
                                context,
                                "Cleared all bookmarks and cache",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    title = "Clear complete data",
                    description = "Remove all bookmarks and cache, although cache will be re-added in your local storage when you scroll through screens which will be deleted automatically after 24 Hours accordingly."
                )
            }
            item {
                DividerComposable()
            }
            item {
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = md_theme_dark_onSurface,
                    modifier = Modifier.titlePadding()
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.redirectToWeb(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        webURL = "https://2fpvxo3g.bearblog.dev/open-source-libraries-for-arara-android-client/"
                    ),
                    title = "Open-source libraries",
                    description = "Sweet software that helped this project much more than anyone could think!"
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier,
                    title = "Privacy",
                    description = "Every bit of data is stored locally on your device itself!"
                )
            }
            item {
                IndividualSettingItemComposable(
                    modifier = Modifier.redirectToWeb(
                        sharedViewModel = sharedViewModel,
                        navController = navController,
                        webURL = "https://github.com/sakethpathike/arara-android"
                    ),
                    title = "Source code",
                    description = "The codebase of this application is public and open-source, you can check code-base if you want to know how this app is built or if you think your data is being collected *_*"
                )
            }
            item {
                GIFThing(
                    imgURL = HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                    modifier = Modifier
                        .background(md_theme_dark_surface)
                        .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                        .fillMaxWidth()
                        .height(70.dp)
                )
            }
        }
    }
}

fun Modifier.titlePadding(): Modifier {
    return this.padding(start = 15.dp, top = 25.dp, end = 20.dp)
}

fun Modifier.descriptionPadding(): Modifier {
    return this.padding(start = 15.dp, top = 8.dp, end = 20.dp)
}

@Composable
fun DividerComposable() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 25.dp, bottom = 0.dp),
        startIndent = 1.dp,
        color = Color.DarkGray
    )
}

suspend fun changeInAppBrowserSetting(
    settingsPreferenceKey: Preferences.Key<Boolean> = preferencesKey(
        "settingsPreferences"
    ), dataStore: DataStore<Preferences>, isInAppBrowsing: Boolean
) {
    dataStore.edit {
        it[settingsPreferenceKey] = isInAppBrowsing
    }
}

suspend fun readInAppBrowserSetting(
    dataStore: DataStore<Preferences>,
    settingsPreferenceKey: Preferences.Key<Boolean> = preferencesKey(
        "settingsPreferences"
    )
) {
    val preference = dataStore.data.first()[settingsPreferenceKey]
    if (preference != null) {
        Settings.inAppBrowserSetting.value = preference
    }
}

object Settings {
    val inAppBrowserSetting = mutableStateOf(true)
}

fun Modifier.redirectToWeb(
    sharedViewModel: SharedViewModel, navController: NavController, webURL: String
): Modifier = composed {
    val localUriHandler = LocalUriHandler.current
    this.clickable {
        if (Settings.inAppBrowserSetting.value) {
            sharedViewModel.assignPermalink(permalink = webURL)
            navController.navigate("webView")
        } else {
            localUriHandler.openUri(webURL)
        }
    }
}

@Composable
fun IndividualSettingItemComposable(
    modifier: Modifier, title: String, description: String, lineHeight: TextUnit = 18.sp
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            color = md_theme_dark_onSurface,
            modifier = Modifier.titlePadding()
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier.descriptionPadding(),
            lineHeight = lineHeight
        )
    }
}