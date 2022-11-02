package com.sakethh.arara.home.subHomeScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.sakethh.arara.*
import com.sakethh.arara.R
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.home.selectedChipStuff.apiData.SubRedditData
import com.sakethh.arara.home.shimmer
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun SubHomeScreen(navController: NavController, sharedViewModel: SharedViewModel,
                  bottomSheetScaffoldState: BottomSheetScaffoldState) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        BottomNavigationBar.isBottomBarHidden.value = false
        navController.navigate("homeScreen") {
            popUpTo(0)
        }
    }
    val bottomPaddingForGIF = if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
        80.dp
    } else {
        0.dp
    }
    sharedViewModel.isBottomNavVisible.value=false
    MaterialTheme(typography = Typography) {
        Column(modifier = Modifier.background(md_theme_dark_surface)) {
            LazyColumn(
                modifier = Modifier.background(md_theme_dark_surface)
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
                                text = sharedViewModel._currentHeaderHomeScreen.value,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 24.sp,
                                color = md_theme_dark_onSurface
                            )
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface)
                    )
                }
                stickyHeader {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = md_theme_dark_surface
                    ) {
                        tabsList.forEachIndexed { index, tabsData ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }.start()
                                },
                                modifier = Modifier.padding(
                                    end = 20.dp,
                                    bottom = 20.dp,
                                    start = 20.dp
                                )
                            ) {
                                Text(
                                    text = tabsData.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = md_theme_dark_onSurface,
                                    fontSize = 17.sp
                                )
                            }
                        }
                    }
                }
            }
            HorizontalPager(count = tabsList.size, state = pagerState) {
                val screensList = listOf(
                    SubHomeScreen {//hot
                        when (sharedViewModel._currentHeaderHomeScreen.value) {
                            "Fanarts" -> {
                                if (SubHomeScreenVM.Fanarts.isDataLoaded.value) {
                                    HotScreen(
                                        dataList = SubHomeScreenVM.Fanarts.hot.value,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel,
                                        bottomPaddingForGIF =bottomPaddingForGIF
                                    )
                                } else {
                                    NonLoadedComposable()

                                }

                            }
                            "News" -> {
                                if (SubHomeScreenVM.News.isDataLoaded.value) {
                                    HotScreen(
                                        dataList = SubHomeScreenVM.News.hot.value,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel,
                                        bottomPaddingForGIF =bottomPaddingForGIF
                                    )
                                } else {
                                    NonLoadedComposable()

                                }

                            }
                            "Images" -> {
                                if (SubHomeScreenVM.Images.isDataLoaded.value) {
                                    HotScreen(
                                        dataList = SubHomeScreenVM.Images.hot.value,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel,
                                    bottomPaddingForGIF =bottomPaddingForGIF
                                    )
                                } else {
                                    NonLoadedComposable()

                                }

                            }
                        }

                    },
                    SubHomeScreen {//rel
                        when (sharedViewModel._currentHeaderHomeScreen.value) {
                            "Fanarts" -> {
                                if (SubHomeScreenVM.Fanarts.isDataLoaded.value) {
                                    RelevanceScreen(
                                        dataList = SubHomeScreenVM.Fanarts.relevance.value,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel,
                                        bottomPaddingForGIF =bottomPaddingForGIF
                                    )
                                } else {
                                    NonLoadedComposable()

                                }
                            }
                            "News" -> {
                                if (SubHomeScreenVM.News.isDataLoaded.value) {
                                    RelevanceScreen(
                                        dataList = SubHomeScreenVM.News.relevance.value,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel,
                                        bottomPaddingForGIF =bottomPaddingForGIF
                                    )
                                } else {
                                    NonLoadedComposable()

                                }
                            }
                            "Images" -> {
                                if (SubHomeScreenVM.Images.isDataLoaded.value) {
                                    RelevanceScreen(
                                        dataList = SubHomeScreenVM.Images.relevance.value,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel,
                                        bottomPaddingForGIF =bottomPaddingForGIF
                                    )
                                } else {
                                    NonLoadedComposable()
                                }
                            }
                        }
                    },
                    SubHomeScreen {//top
                        when (sharedViewModel._currentHeaderHomeScreen.value) {
                            "Fanarts" -> {
                                TopScreen(
                                    topAllTimeData = SubHomeScreenVM.Fanarts.topAllTime.value,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel,
                                    topPast24Hours = SubHomeScreenVM.Fanarts.topPast24Hours.value,
                                    topPastHour = SubHomeScreenVM.Fanarts.topPastHour.value,
                                    topPastMonth = SubHomeScreenVM.Fanarts.topPastMonth.value,
                                    topPastWeek = SubHomeScreenVM.Fanarts.topPastWeek.value,
                                    topPastYear = SubHomeScreenVM.Fanarts.topPastYear.value,
                                    bottomPaddingForGIF =bottomPaddingForGIF
                                )
                            }
                            "News" -> {
                                TopScreen(
                                    topAllTimeData = SubHomeScreenVM.News.relevance.value,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel,
                                    topPast24Hours = SubHomeScreenVM.News.topPast24Hours.value,
                                    topPastHour = SubHomeScreenVM.News.topPastHour.value,
                                    topPastMonth = SubHomeScreenVM.News.topPastMonth.value,
                                    topPastWeek = SubHomeScreenVM.News.topPastWeek.value,
                                    topPastYear = SubHomeScreenVM.News.topPastYear.value,
                                    bottomPaddingForGIF =bottomPaddingForGIF
                                )

                            }
                            "Images" -> {
                                TopScreen(
                                    topAllTimeData = SubHomeScreenVM.News.relevance.value,
                                    navController = navController,
                                    sharedViewModel = sharedViewModel,
                                    topPast24Hours = SubHomeScreenVM.News.topPast24Hours.value,
                                    topPastHour = SubHomeScreenVM.News.topPastHour.value,
                                    topPastMonth = SubHomeScreenVM.News.topPastMonth.value,
                                    topPastWeek = SubHomeScreenVM.News.topPastWeek.value,
                                    topPastYear = SubHomeScreenVM.News.topPastYear.value,
                                    bottomPaddingForGIF =bottomPaddingForGIF
                                )
                            }
                        }
                    }
                )
                screensList[pagerState.currentPage].composable()

            }
        }
    }
}


@Composable
fun NonLoadedComposable() {
    val context = LocalContext.current
    val constraintSet = ConstraintSet {
        val titleForCard = createRefFor("titleForCard")
        val descriptionIcon = createRefFor("descriptionIcon")
        val dropDownIcon = createRefFor("dropDownIcon")
        val dropDownComposable = createRefFor("dropDownComposable")
        val image = createRefFor("image")
        constrain(titleForCard) {
            top.linkTo(image.bottom)
            start.linkTo(parent.start)
        }
        constrain(descriptionIcon) {
            top.linkTo(image.bottom)
            end.linkTo(parent.end)
        }
        constrain(dropDownIcon) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(dropDownComposable) {
            top.linkTo(dropDownIcon.bottom)
            end.linkTo(parent.end)
        }
    }
    LazyColumn(modifier = Modifier.background(md_theme_dark_surface)) {
        items(5) {
            Card(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = md_theme_dark_onPrimary)
            ) {
                ConstraintLayout(
                    constraintSet = constraintSet,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .animateContentSize()
                ) {
                    ImageThing(
                        model = ImageRequest.Builder(context).data("imgLink")
                            .crossfade(true).build(),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .layoutId("image")
                            .shimmer(true),
                        onError = painterResource(id = randomLostInternetImg())
                    )
                    Text(
                        text = "wefhneawfjnefneklsafnlsenfoashefkljhsnaefkljneawkl",
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
                            .shimmer(true)
                    )
                    Text(
                        text = "wefhefbaefs",
                        color = md_theme_dark_primary,
                        fontSize = 10.sp,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        lineHeight = 10.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                            .shimmer(true)
                    )
                }
            }
        }
    }
}

typealias Page = @Composable () -> Unit

data class SubHomeScreen(
    val composable: Page
)

data class TabsData(
    val name: String
)

val tabsList = listOf(
    TabsData(name = "Hot"),
    TabsData(name = "Relevance"),
    TabsData(name = "Top")
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HotScreen(
    dataList: List<SubRedditData>,
    navController: NavController,
    sharedViewModel: SharedViewModel,
    bottomPaddingForGIF:Dp
) {
    LazyColumn(modifier = Modifier.background(md_theme_dark_surface)) {
        itemsIndexed(dataList.component1()) { index, item ->
            if (!item.data.is_video && item.data.url.contains(
                    regex = Regex(
                        "/i.redd.it"
                    )
                )
            ) {
                SelectedChipComposable(
                    navController = navController,
                    imgLink = item.data.url,
                    title = item.data.title,
                    author = item.data.author,
                    index = index,
                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                    indexOnClick = {
                        HomeScreenViewModel.Utils.nonIndexedValue.value = it
                    },
                    permalink = item.data.permalink,
                    sharedViewModel = sharedViewModel
                )
            }
        }
        item {
            GIFThing(
                imgURL =
                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                modifier = Modifier
                    .background(md_theme_dark_surface)
                    .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                    .fillMaxWidth()
                    .height(70.dp)
            )
        }
    }
}

@Composable
fun RelevanceScreen(
    dataList: List<SubRedditData>,
    navController: NavController,
    sharedViewModel: SharedViewModel,
    bottomPaddingForGIF:Dp
) {
    LazyColumn(modifier = Modifier.background(md_theme_dark_surface)) {
        itemsIndexed(dataList.component1()) { index, item ->
            if (!item.data.is_video && item.data.url.contains(
                    regex = Regex(
                        "/i.redd.it"
                    )
                )
            ) {
                SelectedChipComposable(
                    navController = navController,
                    imgLink = item.data.url,
                    title = item.data.title,
                    author = item.data.author,
                    index = index,
                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                    indexOnClick = {
                        HomeScreenViewModel.Utils.nonIndexedValue.value = it
                    },
                    permalink = item.data.permalink,
                    sharedViewModel = sharedViewModel
                )
            }

        }
        item {
            GIFThing(
                imgURL =
                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                modifier = Modifier
                    .background(md_theme_dark_surface)
                    .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                    .fillMaxWidth()
                    .height(70.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TopScreen(
    topAllTimeData: List<SubRedditData>,
    topPast24Hours: List<SubRedditData>,
    topPastHour: List<SubRedditData>,
    topPastMonth: List<SubRedditData>,
    topPastWeek: List<SubRedditData>,
    topPastYear: List<SubRedditData>,
    navController: NavController,
    sharedViewModel: SharedViewModel,
    bottomPaddingForGIF:Dp
) {
    if (SubHomeScreenVM.Fanarts.isDataLoaded.value && SubHomeScreenVM.Images.isDataLoaded.value && SubHomeScreenVM.News.isDataLoaded.value) {
        val subHomeScreenVM: SubHomeScreenVM = viewModel()
        val scrollState = rememberScrollState()
        val lazyState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LazyColumn(modifier = Modifier.background(md_theme_dark_surface).fillMaxSize()) {
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
                    subHomeScreenVM.topList.forEach {
                        val containerColor =
                            if (SubHomeScreenVM.Utils.selectedTextFromSubHomeScreenTopSection.value == it) {
                                md_theme_dark_onPrimary
                            } else {
                                md_theme_dark_surface
                            }
                        val labelColor =
                            if (SubHomeScreenVM.Utils.selectedTextFromSubHomeScreenTopSection.value == it) {
                                md_theme_dark_primary
                            } else {
                                md_theme_dark_onSurface
                            }
                        val iconColor =
                            if (SubHomeScreenVM.Utils.selectedTextFromSubHomeScreenTopSection.value == it) {
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
                        if (SubHomeScreenVM.Utils.selectedTextFromSubHomeScreenTopSection.value == it) {
                            currentRedditIcon[0] = redditIcons[0]
                        } else {
                            currentRedditIcon[0] = redditIcons[1]
                        }
                        AssistChip(
                            onClick = {
                                SubHomeScreenVM.Utils.selectedTextFromSubHomeScreenTopSection.value =
                                    it

                                if (!lazyState.isScrollInProgress) {
                                    coroutineScope.launch {
                                        lazyState.animateScrollToItem(0)
                                    }.start()
                                }
                            },
                            label = {
                                Text(
                                    text = it, modifier = Modifier.shimmer()
                                )
                            },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = currentRedditIcon[0]),
                                    contentDescription = "reddit icon",
                                    modifier = Modifier
                                        .size(AssistChipDefaults.IconSize)
                                )
                            }, colors = AssistChipDefaults.assistChipColors(
                                containerColor = containerColor,
                                labelColor = labelColor,
                                leadingIconContentColor = iconColor
                            ), modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                }
            }
            when (SubHomeScreenVM.Utils.selectedTextFromSubHomeScreenTopSection.value) {
                "All Time" -> {
                    itemsIndexed(topAllTimeData.component1()) { index, item ->
                        if (!item.data.is_video && item.data.url.contains(
                                regex = Regex(
                                    "/i.redd.it"
                                )
                            )
                        ) {
                            SelectedChipComposable(
                                navController = navController,
                                imgLink = item.data.url,
                                title = item.data.title,
                                author = item.data.author,
                                index = index,
                                indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                indexOnClick = {
                                    HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                },
                                permalink = item.data.permalink,
                                sharedViewModel = sharedViewModel
                            )
                        }

                    }
                    item {
                        GIFThing(
                            imgURL =
                            HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                            modifier = Modifier
                                .background(md_theme_dark_surface)
                                .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                                .fillMaxWidth()
                                .height(70.dp)
                        )
                    }
                }
                "Past 24 Hours" -> {
                    if (topPast24Hours.component1().isEmpty()) {
                        item {
                            EmptyArrayResponseComposable()
                        }
                    } else {
                        itemsIndexed(topPast24Hours.component1()) { index, item ->
                            if (!item.data.is_video && item.data.url.contains(
                                    regex = Regex(
                                        "/i.redd.it"
                                    )
                                )
                            ) {
                                SelectedChipComposable(
                                    navController = navController,
                                    imgLink = item.data.url,
                                    title = item.data.title,
                                    author = item.data.author,
                                    index = index,
                                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                    indexOnClick = {
                                        HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                    },
                                    permalink = item.data.permalink,
                                    sharedViewModel = sharedViewModel
                                )
                            }

                        }
                        item {
                            GIFThing(
                                imgURL =
                                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                                modifier = Modifier
                                    .background(md_theme_dark_surface)
                                    .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                                    .fillMaxWidth()
                                    .height(70.dp)
                            )
                        }
                    }
                }
                "Past Hour" -> {
                    if (topPastHour.component1().isEmpty()) {
                        item {
                            EmptyArrayResponseComposable()
                        }
                    } else {

                        itemsIndexed(topPastHour.component1()) { index, item ->
                            if (!item.data.is_video && item.data.url.contains(
                                    regex = Regex(
                                        "/i.redd.it"
                                    )
                                )
                            ) {
                                SelectedChipComposable(
                                    navController = navController,
                                    imgLink = item.data.url,
                                    title = item.data.title,
                                    author = item.data.author,
                                    index = index,
                                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                    indexOnClick = {
                                        HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                    },
                                    permalink = item.data.permalink,
                                    sharedViewModel = sharedViewModel
                                )
                            }

                        }
                        item {
                            GIFThing(
                                imgURL =
                                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                                modifier = Modifier
                                    .background(md_theme_dark_surface)
                                    .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                                    .fillMaxWidth()
                                    .height(70.dp)
                            )
                        }
                    }
                }
                "Past Month" -> {
                    if (topPastMonth.component1().isEmpty()) {
                        item {
                            EmptyArrayResponseComposable()
                        }
                    } else {
                        itemsIndexed(topPastMonth.component1()) { index, item ->
                            if (!item.data.is_video && item.data.url.contains(
                                    regex = Regex(
                                        "/i.redd.it"
                                    )
                                )
                            ) {
                                SelectedChipComposable(
                                    navController = navController,
                                    imgLink = item.data.url,
                                    title = item.data.title,
                                    author = item.data.author,
                                    index = index,
                                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                    indexOnClick = {
                                        HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                    },
                                    permalink = item.data.permalink,
                                    sharedViewModel = sharedViewModel
                                )
                            }

                        }
                        item {
                            GIFThing(
                                imgURL =
                                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                                modifier = Modifier
                                    .background(md_theme_dark_surface)
                                    .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                                    .fillMaxWidth()
                                    .height(70.dp)
                            )
                        }
                    }
                }
                "Past Week" -> {
                    if (topPastWeek.component1().isEmpty()) {
                        item {
                            EmptyArrayResponseComposable()
                        }
                    } else {
                        itemsIndexed(topPastWeek.component1()) { index, item ->
                            if (!item.data.is_video && item.data.url.contains(
                                    regex = Regex(
                                        "/i.redd.it"
                                    )
                                )
                            ) {
                                SelectedChipComposable(
                                    navController = navController,
                                    imgLink = item.data.url,
                                    title = item.data.title,
                                    author = item.data.author,
                                    index = index,
                                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                    indexOnClick = {
                                        HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                    },
                                    permalink = item.data.permalink,
                                    sharedViewModel = sharedViewModel
                                )
                            }

                        }
                        item {
                            GIFThing(
                                imgURL =
                                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                                modifier = Modifier
                                    .background(md_theme_dark_surface)
                                    .padding(top = 10.dp, bottom = bottomPaddingForGIF)
                                    .fillMaxWidth()
                                    .height(70.dp)
                            )
                        }
                    }
                }
                "Past Year" -> {
                    itemsIndexed(topPastYear.component1()) { index, item ->
                        if (!item.data.is_video && item.data.url.contains(
                                regex = Regex(
                                    "/i.redd.it"
                                )
                            )
                        ) {
                            SelectedChipComposable(
                                navController = navController,
                                imgLink = item.data.url,
                                title = item.data.title,
                                author = item.data.author,
                                index = index,
                                indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                indexOnClick = {
                                    HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                },
                                permalink = item.data.permalink,
                                sharedViewModel = sharedViewModel
                            )
                        }

                    }
                    item {
                        GIFThing(
                            imgURL =
                            HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
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
    } else {
        NonLoadedComposable()
    }
}

@Composable
fun EmptyArrayResponseComposable() {
    Column(
        modifier = Modifier.background(md_theme_dark_surface).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = randomNoBookmarksImg()),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = randomNoDataInArrayTxt(),
            style = MaterialTheme.typography.titleMedium,
            color = md_theme_dark_onSurface,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

fun randomNoDataInArrayTxt(): String {
    return listOf(
        "There's nothing here",
        "404",
        "Four-0-Four",
        "How about switching to another screen?",
        "Server couldn't find any data regarding this filter:("
    ).random()
}