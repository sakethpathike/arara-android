package com.sakethh.arara.home.subHomeScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.sakethh.arara.GIFThing
import com.sakethh.arara.R
import com.sakethh.arara.SharedViewModel
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.home.HomeScreenViewModel.RetrievedSubRedditData.imagesHotData
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class
)
@Composable
fun SubHomeScreen(headerText: String = "Fanarts", navController: NavController, sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    MaterialTheme(typography = Typography) {
        Column(modifier = Modifier.background(md_theme_dark_surface)) {
            LazyColumn(
                modifier = Modifier.background(md_theme_dark_surface)
            ) {
                item {
                        SmallTopAppBar(
                            title = {
                                Text(
                                    text = headerText,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 24.sp,
                                    color = md_theme_dark_onSurface
                                )
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface)
                        )
                        ImageThing(
                            model = ImageRequest.Builder(context).crossfade(true)
                                .data("https://yt3.ggpht.com/yYX5Dz_k6BgsWPrpcIbUK8LS9Gvd8MJ74GFtaiYUUYAzEcFmcKwPNS27NjFns1IxOenGkUKp=w1280-fcrop64=1,32b75a57cd48a5a8-k-c0xffffffff-no-nd-rj")
                                .build(),
                            contentDescription = "null",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            onError = painterResource(id = R.drawable.image),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cover-art stolen from xyz from abc",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 16.sp,
                                color = md_theme_dark_onSurface
                            )
                        }

                }
                stickyHeader {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = md_theme_dark_surface
                    ) {
                        tabsList.forEachIndexed { index, tabsData ->
                            Tab(
                                selected = pagerState.currentPage == index, onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }.start()
                                }, modifier = Modifier.padding(20.dp)
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
                LazyColumn(
                    modifier = Modifier.background(md_theme_dark_surface)
                ) {
                    itemsIndexed(imagesHotData.value.component1()) { index, dataItem ->
                        if (!dataItem.data.is_video && dataItem.data.url.contains(
                                regex = Regex(
                                    "/i.redd.it"
                                )
                            )
                        ) {
                            val screensList = listOf(
                                SubHomeScreen {
                                    CurrentSubHomeScreen(imgLink = dataItem.data.url,
                                        title = "Screen0",
                                        author = dataItem.data.author,
                                        index = index,
                                        indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                        indexOnClick = {
                                            HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                        },
                                        permalink = dataItem.data.permalink,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel)
                                },
                                SubHomeScreen {
                                    CurrentSubHomeScreen(imgLink = dataItem.data.url,
                                        title = "Screen1",
                                        author = dataItem.data.author,
                                        index = index,
                                        indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                        indexOnClick = {
                                            HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                        },
                                        permalink = dataItem.data.permalink,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel)
                                },
                                SubHomeScreen {
                                    CurrentSubHomeScreen(imgLink = dataItem.data.url,
                                        title = "Screen2",
                                        author = dataItem.data.author,
                                        index = index,
                                        indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                        indexOnClick = {
                                            HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                        },
                                        permalink = dataItem.data.permalink,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel)
                                },
                                SubHomeScreen {
                                    CurrentSubHomeScreen(imgLink = dataItem.data.url,
                                        title = "Screen3",
                                        author = dataItem.data.author,
                                        index = index,
                                        indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                                        indexOnClick = {
                                            HomeScreenViewModel.Utils.nonIndexedValue.value = it
                                        },
                                    permalink = dataItem.data.permalink,
                                        navController = navController,
                                        sharedViewModel = sharedViewModel)
                                },
                            )
                            screensList[pagerState.currentPage].composable()
                        }

                    }
                }
            }

            GIFThing(
                imgURL =
                HomeScreenViewModel.RetrievedSubRedditData.footerGIFURL.value[0].footerImg,
                modifier = Modifier
                    .background(md_theme_dark_surface)
                    .fillMaxWidth()
                    .height(70.dp)
            )
        }
}}


@Composable
fun CurrentSubHomeScreen(
    imgLink: String,
    title: String,
    author: String,
    index: Int,
    indexedValue: Int,
    indexOnClick: (Int) -> Unit,
    permalink:String,
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    SelectedChipComposable(
        imgLink = imgLink,
        title = title,
        author = author,
        index = index,
        indexedValue = indexedValue,
        indexOnClick = indexOnClick,
        permalink = permalink,
        navController = navController,
        sharedViewModel = sharedViewModel
    )
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
    TabsData(name = "Top"),
    TabsData(name = "New")
)