package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.sakethh.arara.FooterGIF
import com.sakethh.arara.FooterThing
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.headerColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarScrollState(),
        canScroll = { false },
        decayAnimationSpec = rememberSplineBasedDecay()
    )
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
    }) {
        LazyColumn(contentPadding = it) {
            item { ArtBoard() }
                val apiUrl = "https://sample-server-side.herokuapp.com/unreleased"
                val request = apiUrl.httpGet()
                CoroutineScope(Dispatchers.Default).launch {
                    val fetchData = async {
                        request.responseJson { request, response, result ->
                            val doc = result.get().array()
                            items(doc.length()) {
                                SongThing(
                                    imageLink = doc.getJSONObject(it).getString("specificArtwork")
                                        .toString(),
                                    songName = doc.getJSONObject(it).getString("songName")
                                ).toString()
                            }
                        }
                    }
                    fetchData.await()
                }.start()
            item { FooterGIF() }
        }
    }
}
//https://ia802501.us.archive.org/9/items/aurora-artworks/cl31a8pbe00cil9qq5adohc90-footer_a%402x.gif