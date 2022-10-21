package com.sakethh.arara.home.settings

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.sakethh.arara.GIFThing
import com.sakethh.arara.R
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.ui.theme.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(dataStore: DataStore<Preferences>) {
    val lineHeight = 18.sp
    val coroutineScope = rememberCoroutineScope()
    MaterialTheme(typography = Typography) {
        LazyColumn(
            modifier = Modifier
                .background(md_theme_dark_surface)
                .fillMaxSize()
        ) {
            item {
                SmallTopAppBar(
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
                        Switch(
                            checked = Settings.inAppBrowserSetting.value,
                            modifier = Modifier
                                .padding(end = 20.dp),
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
                                        modifier = Modifier
                                            .size(28.dp)
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
                Column {
                    Text(
                        text = "Subreddit of uncle arara",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Most of the data is collected from r/auroramusic",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                Column {
                    Text(
                        text = "API",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Data from r/auroramusic is fetched by an API which returns data from subreddit.",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                Column {
                    Text(
                        text = "Icons",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Every icon used in this project is stolen from tabler-icons.io",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
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
                Column {
                    Text(
                        text = "Clear cache",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "You can free up storage by clearing your cache. Your bookmarks won't be removed.",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                Column {
                    Text(
                        text = "Clear Bookmarks",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Remove all bookmarks from local database. Your cache won't be removed.",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                Column {
                    Text(
                        text = "Clear complete data",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Remove all bookmarks and cache, although cache will be re-added in your local storage when you scroll through screens which will be deleted automatically after 24 Hours accordingly.",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
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
                Column {
                    Text(
                        text = "Open-source libraries",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Sweet software that helped this project much more than anyone could think!",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                Column {
                    Text(
                        text = "Privacy",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "Every bit of data is stored locally on your device itself!",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                Column {
                    Text(
                        text = "Source code",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = md_theme_dark_onSurface,
                        modifier = Modifier.titlePadding()
                    )
                    Text(
                        text = "The codebase of this application is public and open-source, you can check code-base if you want to know how this app is built or if you think your data is being collected *_*",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.descriptionPadding(),
                        lineHeight = lineHeight
                    )
                }
            }
            item {
                GIFThing(
                    imgURL =
                    HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                    modifier = Modifier
                        .background(md_theme_dark_surface)
                        .padding(top = 10.dp, bottom = 80.dp)
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
suspend fun changeInAppBrowserSetting(settingsPreferenceKey : Preferences.Key<Boolean> = preferencesKey("settingsPreferences"),dataStore: DataStore<Preferences>,isInAppBrowsing: Boolean) {
    dataStore.edit {
        it[settingsPreferenceKey] = isInAppBrowsing
    }
}

suspend fun readInAppBrowserSetting(dataStore: DataStore<Preferences>,settingsPreferenceKey : Preferences.Key<Boolean> = preferencesKey("settingsPreferences")) {
    val preference = dataStore.data.first()[settingsPreferenceKey]
    if (preference != null) {
        Settings.inAppBrowserSetting.value = preference
    }
}

object Settings {
    val inAppBrowserSetting = mutableStateOf(true)
}