package com.sakethh.arara.unreleased

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sakethh.arara.R

@Composable
fun SongThing(imageLink: String) {
    val paddingValue = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(80.dp)
            .padding(
                start = paddingValue,
                end = paddingValue,
                top = paddingValue,
                bottom = paddingValue
            )
            .border(width = 3.dp, color = Color.DarkGray)
            .background(color = Color.Transparent)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageLink,
                contentDescription = "Image for unreleased song from a warrior:)",
                modifier = Modifier
                    .padding(top = 10.dp) //gives 10dp padding in top
                    .requiredHeight(40.dp) // renders height of the image
                    .padding(start = 10.dp) //gives 10dp padding in left
                    .requiredWidth(40.dp) /*renders width of the image*/,
                error = painterResource(R.drawable.image)
            )
            Column(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .fillMaxHeight()
                    .wrapContentWidth()
            ) {
                Text(
                    text = "Shelff!",
                    modifier = Modifier.requiredSize(10.dp),
                    fontFamily = FontFamily.Monospace,
                    color = Color.DarkGray
                )
                Text(
                    text = "Lyrics",
                    color = Color.Gray,
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .requiredSize(5.dp),
                    fontFamily = FontFamily.Monospace
                )
            }
        }

    }
}