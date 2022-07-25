package com.sakethh.arara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sakethh.arara.ui.theme.AraraTheme
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.unreleased.SongThing

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(typography = Typography /*(typography varible name from Type.kt)*/) {
                SongThing(imageLink = "https://ia802505.us.archive.org/30/items/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SongThing(imageLink = "https://archive.org/details/artworks-from-shangela-laquifa-warrior/Wake%20Up.png")
    }
}