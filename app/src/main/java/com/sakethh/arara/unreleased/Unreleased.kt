package com.sakethh.arara.unreleased

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sakethh.arara.GIFThing
import com.sakethh.arara.MainActivity
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.headerColor
import com.sakethh.arara.unreleased.currentMusicScreen.CurrentMusicScreenViewModel
import okhttp3.Cache
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen(itemOnClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    ) { false }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedData=unreleasedViewModel.rememberData
    val headerData = unreleasedViewModel.rememberUnreleasedHeaderImg.value
    val footerData = unreleasedViewModel.rememberUnreleasedFooterImg.value
    val musicPlayerImgURL = unreleasedViewModel.rememberMusicPlayerImgURL
    val musicPlayerHDImgURL = unreleasedViewModel.rememberMusicPlayerHDImgURL
    val musicPlayerTitle = unreleasedViewModel.rememberMusicPlayerTitle
    val unreleasedLyricsForPlayer = unreleasedViewModel.rememberMusicPlayerLyrics
    val rememberMusicPlayerDescription = unreleasedViewModel.rememberMusicPlayerDescription
    val rememberMusicPlayerDescriptionBy = unreleasedViewModel.rememberMusicPlayerDescriptionBy
    val rememberMusicPlayerArtworkBy = unreleasedViewModel.rememberMusicPlayerArtworkBy
    val rememberMusicPlayerDescriptionOrigin =
        unreleasedViewModel.rememberMusicPlayerDescriptionOrigin
    val audioUrl=unreleasedViewModel.musicAudioURL
    Scaffold(modifier = Modifier.background(backgroundColor), topBar = {
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

            items(unreleasedData.value) { data ->
                SongThing1(
                    songName = data.songName,
                    specificArtwork = data.imgURL
                ) {
                    musicPlayerImgURL.value = data.imgURL
                    musicPlayerTitle.value = data.songName
                    unreleasedLyricsForPlayer.value = data.lyrics
                    rememberMusicPlayerDescription.value = data.songDescription
                    rememberMusicPlayerDescriptionBy.value = data.descriptionBy
                    rememberMusicPlayerDescriptionOrigin.value = data.descriptionOrigin
                    rememberMusicPlayerArtworkBy.value = data.specificArtworkBy
                    audioUrl.value=data.audioLink
                    musicPlayerHDImgURL.value=data.imgURLHD
                    itemOnClick()
                }
            }
            items(footerData) { data ->
                if(unreleasedViewModel.musicPlayerActivate.value){
                    GIFThing(
                        imgURL = data.footerImg, modifier = Modifier
                            .background(backgroundColor)
                            .padding(bottom = 100.dp)
                            .fillMaxWidth()
                            .height(70.dp)
                    )
                }else{
                    GIFThing(
                        imgURL = data.footerImg, modifier = Modifier
                            .background(backgroundColor)
                            .fillMaxWidth()
                            .height(70.dp)
                    )
                }
            }
        }
    }
}

