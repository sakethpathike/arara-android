package com.sakethh.arara

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
