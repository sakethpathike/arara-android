package com.sakethh.arara.unreleased

import android.util.Log
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.sakethh.arara.FooterGIF
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.headerColor
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberSplineBasedDecay(),
        rememberTopAppBarState()
    ) { false }
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
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
            modifier = Modifier.background(backgroundColor),
            contentPadding = contentPadding,
        ) {
            item{ ArtBoard()}
            items(unreleasedViewModel.getSongsData().size) {
                SongThing(
                    songName = unreleasedViewModel.getSongsData()[it].songName,
                    specificArtwork = unreleasedViewModel.getSongsData()[it].imgURL
                )
            }
            item { FooterGIF() }
        }
    }
}

