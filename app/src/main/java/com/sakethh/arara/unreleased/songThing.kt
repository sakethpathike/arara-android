package com.sakethh.arara.unreleased

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.generalFont

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
            .shadow(elevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageLink).crossfade(true)
                    .build(),
                contentDescription = "Image for unreleased song from a warrior:)",
                modifier = Modifier
                    .padding(top = 10.dp) //gives 10dp padding in top
                    .requiredHeight(40.dp) // renders height of the image
                    .padding(start = 10.dp) //gives 10dp padding in left
                    .requiredWidth(40.dp) //renders width of the image
                    .shadow(elevation = 1.dp)
                    .border(BorderStroke(width = 2.dp, color = Color.DarkGray))
                    .blur(4.dp),
                error = painterResource(R.drawable.image)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 60.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Broken Satellites, Wake Up, Architect, The Gods We Can Touch And Many More",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Lyrics",
                    modifier = Modifier
                        .padding(top=4.dp, bottom = 4.dp)
                        .background(color = Color.LightGray)
                        .wrapContentSize()
                        .padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp, color = Color.DarkGray
                )
            }
        }

    }
}