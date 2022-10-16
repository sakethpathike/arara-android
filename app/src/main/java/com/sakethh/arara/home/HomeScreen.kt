package com.sakethh.arara.home

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.GIFThing
import com.sakethh.arara.home.HomeScreenViewModel.Utils.nonIndexedValue
import com.sakethh.arara.home.HomeScreenViewModel.Utils.selectedTextForHomeScreen
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.home.selectedChipStuff.SelectedChipScreenViewModel
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
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
                            dataList = fanArtsHotData.value.take(8),
                            navController = navController,
                            navigationRoute = "subHomeScreen"
                        )
                        MainHomeScreen(headingName = "News", dataList = newsHotData.value.take(8),
                            navController = navController,
                            navigationRoute = "subHomeScreen")
                        MainHomeScreen(
                            headingName = "Images",
                            dataList = imagesHotData.value.take(8),
                            navController = navController,
                            navigationRoute = "subHomeScreen"
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
fun MainHomeScreen(headingName: String, dataList: List<SubRedditData>,navController: NavController,navigationRoute:String) {
    val context = LocalContext.current
    val selectedChipScreenViewModel:SelectedChipScreenViewModel = viewModel()
    val constraintSet = ConstraintSet {
        val dropDownIcon = createRefFor("dropDownIcon")
        val dropDownComposable = createRefFor("dropDownComposable")
        val cardData = createRefFor("cardData")
        constrain(dropDownIcon) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(dropDownComposable) {
            top.linkTo(dropDownIcon.bottom)
            end.linkTo(parent.end)
        }
        constrain(cardData) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, bottom = 15.dp)
            .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = headingName,
            color = md_theme_dark_onSurface,
            fontSize = 23.sp,
            modifier = Modifier
                .padding(start = 10.dp),
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(
            onClick = { navController.navigate(navigationRoute) }, modifier = Modifier
                .padding(end = 10.dp)
                .size(28.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "home detailed screen",
                modifier = Modifier.size(28.dp)
            )
        }
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(dataList.component1()) {
            if (!it.data.is_video && it.data.url.contains(
                    regex = Regex(
                        "/i.redd.it"
                    )
                )
            ) {
            Card(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .requiredWidth(250.dp)
                    .requiredHeight(235.dp),
                colors = CardDefaults.cardColors(containerColor = md_theme_dark_onPrimary)
            ) {
                ConstraintLayout(constraintSet = constraintSet) {

                    val dropDownMenuEnabled = remember{ mutableStateOf(false) }
                    Column(modifier = Modifier.layoutId("cardData")) {
                        ImageThing(
                            model = ImageRequest.Builder(context).data(it.data.url)
                                .crossfade(true).build(),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
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
                        )
                    }
                    IconButton(onClick = {dropDownMenuEnabled.value = !dropDownMenuEnabled.value
                    }, modifier = Modifier.layoutId("dropDownIcon")) {
                        Image(
                            painter = painterResource(id = R.drawable.more_icon),
                            contentDescription = "more_icon",
                            modifier = Modifier
                                .size(24.dp)
                                .shadow(24.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .layoutId(layoutId = "dropDownComposable")
                            .padding(end = 10.dp)
                            .wrapContentSize()
                    ) {
                        DropdownMenu(
                            modifier = Modifier
                                .background(md_theme_dark_onTertiary),
                            expanded = dropDownMenuEnabled.value,
                            onDismissRequest = { dropDownMenuEnabled.value = false }) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Bookmark",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = md_theme_dark_tertiary
                                    )
                                },
                                onClick = {
                                    SelectedChipScreenViewModel.BookMarkedDataUtils.realmDBObject.apply {
                                        this.objectKey = it.data.url
                                        this.imgURL = it.data.url
                                        this.bookMarked = true
                                        this.title = it.data.title
                                        this.author = it.data.author
                                    }.also {
                                        selectedChipScreenViewModel.addToDB()
                                    }
                                    Toast.makeText(context, SelectedChipScreenViewModel.BookMarkedDataUtils.toastMessage.value, Toast.LENGTH_SHORT).show()
                                    dropDownMenuEnabled.value = false
                                },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.bookmark_icon),
                                        contentDescription = "bookmark_icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                })
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Download",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = md_theme_dark_tertiary
                                    )
                                },
                                onClick = {
                                    dropDownMenuEnabled.value = false
                                },
                                leadingIcon = {
                                    Image(
                                        painter = painterResource(id = R.drawable.download_icon),
                                        contentDescription = "download_icon",
                                        modifier = Modifier.size(24.dp)
                                    )
                                })
                            DropdownMenuItem(text = {
                                Text(
                                    text = "Share",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = md_theme_dark_tertiary
                                )
                            }, onClick = {
                                val intent = Intent()
                                intent.action = Intent.ACTION_SEND
                                intent.type = "text/plain"
                                intent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Yo! Sharing \"${it.data.title}\" by \"${it.data.author}\" from reddit; img url :- \"${it.data.url}\""
                                )
                                val shareIntent = Intent.createChooser(intent, "Share using :-")
                                context.startActivity(shareIntent)
                                dropDownMenuEnabled.value = false
                            }, leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.share_icon),
                                    contentDescription = "share_icon",
                                    modifier = Modifier.size(24.dp)
                                )
                            })

                        }
                    }
                }
            }}
        }
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}