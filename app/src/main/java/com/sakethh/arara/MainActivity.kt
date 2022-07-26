package com.sakethh.arara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sakethh.arara.ui.theme.AraraTheme
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.unreleased.SongThing
import com.sakethh.arara.unreleased.UnreleasedScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
               UnreleasedScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(enabled = true, state = ScrollState(0))) {
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
        }    }
}