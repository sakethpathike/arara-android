package com.sakethh.arara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.google.accompanist.pager.HorizontalPager
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.home.subHomeScreen.CurrentSubHomeScreen
import com.sakethh.arara.home.subHomeScreen.SubHomeScreen
import com.sakethh.arara.home.subHomeScreen.tabsList
import com.sakethh.arara.ui.theme.md_theme_dark_onSurface
import com.sakethh.arara.ui.theme.md_theme_dark_surface
import com.sakethh.arara.unreleased.ImageThing
import kotlinx.coroutines.launch

/*

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
                    .data("")
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
            itemsIndexed(fetchedData) { index, dataItem ->
                if (!dataItem.data.is_video && dataItem.data.url.contains(
                        regex = Regex(
                            "/i.redd.it"
                        )
                    )
                ) {
                    val screensList = subHomeScreensList
                    screensList[pagerState.currentPage].composable()
                }

            }
        }
    }
}*/
