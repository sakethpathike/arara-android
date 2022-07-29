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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
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
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.generalFont
import com.sakethh.arara.ui.theme.secondGradient

@Composable
fun SongThing(imageLink: String, songName: String) {
    val paddingValue = 10.dp
    Box(
        modifier = Modifier
            .background(color = backgroundColor)
            .padding(
                top = paddingValue,
                start = paddingValue,
                end = paddingValue
            )
            .fillMaxWidth()
            .requiredHeight(65.dp)
            .border(width = 2.dp, color = Color.LightGray)
            .shadow(2.dp)
            .background(color = backgroundColor)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            ImageThing(
                model = ImageRequest.Builder(LocalContext.current).data(imageLink)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image for unreleased song from a warrior:)",
                modifier = Modifier
                    .padding(top = 10.dp) //gives 10dp padding in top
                    .requiredHeight(45.dp) // renders height of the image
                    .padding(start = 10.dp) //gives 10dp padding in left
                    .requiredWidth(45.dp) //renders width of the image
                    .shadow(elevation = 1.dp),
                onError = painterResource(R.drawable.image)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 60.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = songName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Text(
                    text = "Lyrics",
                    modifier = Modifier
                        .padding(top = 3.dp, bottom = 3.dp)
                        .background(color = Color.Gray)
                        .wrapContentSize()
                        .padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp, fontWeight = FontWeight.Normal, color = backgroundColor
                )
            }
        }

    }
}

@Composable
fun ImageThing(model: Any?, contentDescription: String, modifier: Modifier, onError: Painter) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        error = onError
    )
}

@Composable
fun SongThing1(imageLink: String, songName: String) {
    val paddingValue = 10.dp
    Box(
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxWidth()
            .requiredHeight(65.dp)
            .background(color = backgroundColor)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            ImageThing(
                model = ImageRequest.Builder(LocalContext.current).data(imageLink)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image for unreleased song from a warrior:)",
                modifier = Modifier
                    .padding(top = 10.dp) //gives 10dp padding in top
                    .requiredHeight(50.dp) // renders height of the image
                    .padding(start = 10.dp) //gives 10dp padding in left
                    .requiredWidth(50.dp) //renders width of the image
                , onError = painterResource(R.drawable.image)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 60.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = songName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Text(
                    text = "Lyrics",
                    modifier = Modifier
                        .padding(top = 3.dp, bottom = 3.dp)
                        .background(color = Color.Gray)
                        .wrapContentSize()
                        .padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp, fontWeight = FontWeight.Normal, color = backgroundColor
                )
            }
        }

    }
}

@Composable
fun ArtBoard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
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
