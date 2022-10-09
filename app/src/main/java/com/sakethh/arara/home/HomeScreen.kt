package com.sakethh.arara.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakethh.arara.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.home.HomeScreenViewModel.Utils.nonIndexedValue
import com.sakethh.arara.home.HomeScreenViewModel.Utils.selectedTextForHomeScreen
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val systemUIController = rememberSystemUiController()
    systemUIController.setStatusBarColor(md_theme_dark_surface)
    systemUIController.setNavigationBarColor(md_theme_dark_surface)
    val homeScreenViewModel: HomeScreenViewModel = viewModel()
    val headerList = remember { homeScreenViewModel.listForHeader }
    val currentTime = remember { homeScreenViewModel.currentTime }
    val fanArtsHotData = homeScreenViewModel.fanArtsHotData
    val fanArtsRelevanceData = homeScreenViewModel.fanArtsRelevanceData
    val newsHotData = homeScreenViewModel.newsHotData
    val newsRelevanceData = homeScreenViewModel.newsRelevanceData
    val imagesHotData = homeScreenViewModel.imagesHotData
    val imagesRelevanceData = homeScreenViewModel.imagesRelevanceData
    val currentTimeIsLoaded = remember { homeScreenViewModel.currentTimeLoaded }
    val lazyState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    @Suppress("LocalVariableName")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_surface),
        state = lazyState
    ) {
        item {
            if (!currentTimeIsLoaded.value) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 10.dp, bottom = 15.dp)
                        .height(45.dp)
                        .width(170.dp)
                        .placeholder(
                            visible = !currentTimeIsLoaded.value,
                            color = md_theme_dark_onTertiary,
                            highlight = PlaceholderHighlight.shimmer(highlightColor = md_theme_dark_primaryContainer),
                            shape = RoundedCornerShape(corner = CornerSize(5.dp))
                        )
                )
            } else {
                Text(
                    text = currentTime.value,
                    fontSize = 23.sp,
                    color = md_theme_dark_onSurface,
                    modifier = Modifier
                        .padding(top = 50.dp, start = 10.dp, bottom = 15.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(md_theme_dark_surface)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .wrapContentHeight()
                    .horizontalScroll(state = scrollState)
                    .animateContentSize()
            ) {
                val isCloseButtonPressed = rememberSaveable { mutableStateOf(false) }
                if (isCloseButtonPressed.value) {
                    IconButton(onClick = {
                        isCloseButtonPressed.value = false;selectedTextForHomeScreen.value = ""
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = "close icon",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(24.dp)
                        )
                    }
                }
                headerList.forEach {
                    val containerColor = if (selectedTextForHomeScreen.value == it) {
                        md_theme_dark_onPrimary
                    } else {
                        md_theme_dark_surface
                    }
                    val labelColor = if (selectedTextForHomeScreen.value == it) {
                        md_theme_dark_primary
                    } else {
                        md_theme_dark_onSurface
                    }
                    val iconColor = if (selectedTextForHomeScreen.value == it) {
                        md_theme_dark_primary
                    } else {
                        md_theme_dark_onSurface
                    }
                    val currentRedditIcon = rememberSaveable { mutableListOf(0) }
                    val redditIcons = rememberSaveable {
                        listOf(
                            R.drawable.reddit_icon_selected,
                            R.drawable.reddit_icon_non_selected
                        )
                    }
                    if (selectedTextForHomeScreen.value == it) {
                        currentRedditIcon[0] = redditIcons[0]
                    } else {
                        currentRedditIcon[0] = redditIcons[1]
                    }
                    AssistChip(
                        onClick = {
                            if (currentTimeIsLoaded.value) {
                                selectedTextForHomeScreen.value = it;isCloseButtonPressed.value =
                                    true
                            }
                            if (!lazyState.isScrollInProgress) {
                                coroutineScope.launch {
                                    lazyState.animateScrollToItem(0)
                                }
                            }
                        },
                        label = {
                            Text(
                                text = it, modifier = Modifier.placeholder(
                                    visible = !currentTimeIsLoaded.value,
                                    color = md_theme_dark_onTertiary,
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = md_theme_dark_primaryContainer),
                                    shape = RoundedCornerShape(corner = CornerSize(5.dp))
                                )
                            )
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = currentRedditIcon[0]),
                                contentDescription = "reddit icon",
                                modifier = Modifier
                                    .size(AssistChipDefaults.IconSize)
                                    .placeholder(
                                        visible = !currentTimeIsLoaded.value,
                                        color = md_theme_dark_onTertiary,
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = md_theme_dark_primaryContainer),
                                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                                    )
                            )
                        }, colors = AssistChipDefaults.assistChipColors(
                            containerColor = containerColor,
                            labelColor = labelColor,
                            leadingIconContentColor = iconColor
                        ), modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
        if (currentTime.value == "") {
            currentTimeIsLoaded.value = false
        } else {
            currentTimeIsLoaded.value = true
            when (selectedTextForHomeScreen.value) {
                "Warrior-arts" -> {
                    itemsIndexed(fanArtsHotData.value.component1() + fanArtsRelevanceData.value.component1()) { index, item ->
                        if (!item.data.is_video && item.data.url.contains(
                                regex = Regex(
                                    "/i.redd.it"
                                )
                            )
                        ) {
                            SelectedChipComposable(
                                imgLink = item.data.url,
                                title = item.data.title,
                                author = "Art by :- ${item.data.author}",
                                index = index,
                                indexedValue = nonIndexedValue.value,
                                indexOnClick = {
                                    nonIndexedValue.value = it
                                }
                            )
                        }
                    }
                    coroutineScope.launch {
                        lazyState.animateScrollToItem(lazyState.firstVisibleItemIndex)
                    }
                }
                "News" -> {
                    itemsIndexed(newsHotData.value.component1() + newsRelevanceData.value.component1()) { index, item ->

                        if (!item.data.is_video && item.data.url.contains(
                                regex = Regex(
                                    "/i.redd.it"
                                )
                            )
                        ) {
                            SelectedChipComposable(
                                imgLink = item.data.url,
                                title = item.data.title,
                                author = "Via :- ${item.data.author}",
                                index = index,
                                indexedValue = nonIndexedValue.value,
                                indexOnClick = {
                                    nonIndexedValue.value = it
                                }
                            )
                        }
                    }
                }
                "Images" -> {
                    itemsIndexed(imagesHotData.value.component1() + imagesRelevanceData.value.component1()) { index, item ->
                        if (!item.data.is_video && item.data.url.contains(
                                regex = Regex(
                                    "/i.redd.it"
                                )
                            )
                        ) {
                            SelectedChipComposable(
                                imgLink = item.data.url,
                                title = item.data.title,
                                author = "Via :- ${item.data.author}",
                                index = index,
                                indexedValue = nonIndexedValue.value,
                                indexOnClick = {
                                    nonIndexedValue.value = it
                                }
                            )
                        }
                    }
                }
                else -> {
                    item {
                        Text(
                            text = "Home Screen",
                            color = md_theme_dark_primary
                        )
                    }
                }
            }
        }
    }
}
// relevance  https://i.redd.it/rby73oyg9eq91.jpg