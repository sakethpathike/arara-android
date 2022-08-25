package com.sakethh.arara.unreleased

import android.app.Activity
import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sakethh.arara.CustomThing
import com.sakethh.arara.GIFThing
import com.sakethh.arara.MainActivity
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.headerColor
import kotlinx.coroutines.delay
import okhttp3.Cache

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ) { false }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val songsData = unreleasedViewModel.rememberData.value
    val headerData = unreleasedViewModel.rememberUnreleasedHeaderImg.value
    val footerData = unreleasedViewModel.rememberUnreleasedFooterImg.value
    val musicPlayer=unreleasedViewModel.rememberMusicPlayer
    val musicPlayerImgURL=unreleasedViewModel.rememberMusicPlayerImgURL
    val musicPlayerTitle=unreleasedViewModel.rememberMusicPlayerTitle
    Scaffold(modifier=Modifier.background(backgroundColor),topBar = {
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
                SongThing1(
                    songName = data.songName,
                    specificArtwork = data.imgURL
                ) {
                    musicPlayerImgURL.value=data.imgURL
                    musicPlayerTitle.value=data.songName
                    musicPlayer.value=true
                }
            }
            items(footerData) { data ->
                GIFThing(imgURL = data.footerImg, modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(backgroundColor))
            }
        }
    }
}

