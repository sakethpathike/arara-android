package com.sakethh.arara.unreleased

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sakethh.arara.FooterGIF
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.headerColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberSplineBasedDecay(),
        rememberTopAppBarState()
    ) { false }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val songsData = unreleasedViewModel.rememberData.value
    val headerData = unreleasedViewModel.rememberUnreleasedHeaderImg.value
    val footerData = unreleasedViewModel.rememberUnreleasedFooterImg.value
    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                Text(
                    text = "Unreleased",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.LightGray
                )
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = headerColor)
        )
    }) { contentPadding ->
        LazyColumn(
            modifier = Modifier.background(firstGradient),
            contentPadding = contentPadding,
        ) {
            items(headerData) { data ->
                ArtBoard(data.artwork)
            }
            items(songsData) { data ->
                SongThing(
                    songName = data.songName,
                    specificArtwork = data.imgURL
                )
            }
            items(footerData) { data ->
                FooterGIF(data.footerImg)
            }
        }
    }
}

