package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarColors
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.headerColor
import com.sakethh.arara.ui.theme.secondGradient

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        isAlwaysCollapsed = true,
        isExpandedWhenFirstDisplayed = false,
        centeredTitleAndSubtitle = false,
        collapsedTopBarHeight = 50.dp,
        expandedTopBarMaxHeight = 60.dp,
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(text = "Unreleased", style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
                },
                colors = CollapsingTopBarColors(
                    backgroundColor = headerColor,
                    contentColor = Color.LightGray,
                    backgroundColorWhenCollapsingOrExpanding = headerColor,
                    onBackgroundColorChange = {}),
            )

        }
    )
    {
        LazyColumn(contentPadding = it)
        {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    firstGradient, secondGradient
                                )
                            )
                        )
                ) {
                    ImageThing(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://ia902501.us.archive.org/9/items/aurora-artworks/unreleaed-artweork.jpg")
                            .crossfade(true).build(),
                        contentDescription = "Toolbar Image",
                        modifier = Modifier
                            .size(150.dp)
                            .shadow(2.dp)
                            .align(Alignment.Center),
                        onError = painterResource(
                            R.drawable.image
                        )
                    )
                }
            }

            val apiCall =
                "https://sample-server-side.herokuapp.com/unreleased".httpGet()
                    .responseJson { request, response, result ->
                        val doc = result.get().array()
                        items(doc.length()) {
                            SongThing1(
                                imageLink = doc.getJSONObject(it)
                                    .getString("specificArtwork"),
                                songName = doc.getJSONObject(it).getString("songName")
                            )
                        }
                    }
            apiCall.join()
        }
    }
}