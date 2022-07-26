package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarColors
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.sakethh.arara.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        isAlwaysCollapsed = false,
        isExpandedWhenFirstDisplayed = true,
        centeredTitleAndSubtitle = true,
        collapsedTopBarHeight = 0.dp,
        expandedTopBarMaxHeight = 200.dp,
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopBar(
                scrollBehavior = scrollBehavior,
                subtitle = {
                    ImageThing(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://ia902501.us.archive.org/9/items/aurora-artworks/unreleaed-artweork.jpg")
                            .crossfade(true).build(),
                        contentDescription = "Toolbar Image",
                        modifier = Modifier
                            .size(150.dp)
                            .shadow(2.dp),
                        onError = painterResource(
                            R.drawable.image
                        )
                    )
                },
                title = {
                    Text(text = "", style = MaterialTheme.typography.bodySmall)
                },
                colors = CollapsingTopBarColors(
                    backgroundColor = Color.DarkGray,
                    contentColor = Color.DarkGray,
                    backgroundColorWhenCollapsingOrExpanding = Color.DarkGray,
                    onBackgroundColorChange = {}),
            )

        }
    )
    {
        LazyColumn(contentPadding = it) {
            items(20) {

                SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")

/*
                SongThing1(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
                */
           }
        }
    }

}