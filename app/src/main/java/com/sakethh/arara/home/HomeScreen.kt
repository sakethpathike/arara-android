package com.sakethh.arara.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.sakethh.arara.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.GIFThing
import com.sakethh.arara.home.HomeScreenViewModel.Utils.nonIndexedValue
import com.sakethh.arara.home.HomeScreenViewModel.Utils.selectedTextForHomeScreen
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditDataItem
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val systemUIController = rememberSystemUiController()
    systemUIController.setStatusBarColor(md_theme_dark_surface)
    val bottomPaddingForGIF = 90.dp
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
            .background(md_theme_dark_surface)
            .fillMaxSize(),
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
                if (HomeScreenViewModel.CloseButtonUtils.isCloseButtonPressed.value) {
                    IconButton(onClick = {
                        HomeScreenViewModel.CloseButtonUtils.isCloseButtonPressed.value =
                            false;selectedTextForHomeScreen.value = ""
                    }) {
                        Image(
                            painter = painterResource(id = HomeScreenViewModel.CloseButtonUtils.closeButtonIcon[0]),
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
                                selectedTextForHomeScreen.value =
                                    it; HomeScreenViewModel.CloseButtonUtils.isCloseButtonPressed.value =
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
                        MainHomeScreen(
                            headingName = "Warrior-arts",
                            dataList = fanArtsHotData.value.take(8)
                        )
                        MainHomeScreen(headingName = "News", dataList = newsHotData.value.take(8))
                        MainHomeScreen(
                            headingName = "Images",
                            dataList = imagesHotData.value.take(8)
                        )
                    }
                }
            }
            items(homeScreenViewModel.footerGIFURL.value) { data ->
                GIFThing(
                    imgURL = data.footerImg, modifier = Modifier
                        .background(md_theme_dark_surface)
                        .padding(bottom = bottomPaddingForGIF)
                        .fillMaxWidth()
                        .height(70.dp)
                )
            }
        }
    }
}
// relevance  https://i.redd.it/rby73oyg9eq91.jpg

@Composable
fun MainHomeScreen(headingName: String, dataList: List<SubRedditData>) {
        val context = LocalContext.current
        Box {
            Text(
                text = headingName,
                color = md_theme_dark_onSurface,
                fontSize = 23.sp,
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
                style = MaterialTheme.typography.titleMedium
            )

        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(dataList.component1()) {
                Card(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .requiredWidth(250.dp)
                        .requiredHeight(250.dp),
                    colors = CardDefaults.cardColors(containerColor = md_theme_dark_onPrimary)
                ) {

                    Column {
                        ImageThing(
                            model = ImageRequest.Builder(context).data(it.data.url)
                                .crossfade(true).build(),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .layoutId("image"),
                            onError = painterResource(id = randomLostInternetImg()),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = it.data.title,
                            color = md_theme_dark_primary,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            lineHeight = 22.sp,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 30.dp)
                                .layoutId("titleForCard")
                        )
                        Text(
                            text = it.data.author,
                            color = md_theme_dark_primary,
                            fontSize = 10.sp,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            lineHeight = 10.sp,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                                .layoutId("author")
                        )
                    }
                }
            }
        }

}