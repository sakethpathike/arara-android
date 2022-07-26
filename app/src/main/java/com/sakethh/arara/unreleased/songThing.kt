package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sakethh.arara.R
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*

@Composable
fun SongThing(songName: String, specificArtwork: String, onClick: () -> Any) {
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
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            ImageThing(
                model = ImageRequest.Builder(LocalContext.current).data(specificArtwork)
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
fun ImageThing(
    model: Any?,
    contentDescription: String,
    modifier: Modifier,
    onError: Painter,
    contentScale: ContentScale = ContentScale.Fit,
    onSuccess: (() -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        error = onError,
        contentScale = contentScale,
        onSuccess = { onSuccess?.invoke() },
        onLoading = { onLoading?.invoke() }
    )
}

@Composable
fun SongThing1(
    songName: String, specificArtwork: String, onClick: () -> Unit,
    imgModifier: Modifier = Modifier
        .padding(top = 10.dp)
        .requiredHeight(50.dp)
        .padding(start = 10.dp)
        .requiredWidth(50.dp),
    titleModifier: Modifier = Modifier,
    lyricsModifier: Modifier = Modifier
        .padding(top = 3.dp, bottom = 3.dp)
        .background(color = md_theme_light_outline)
        .wrapContentSize()
        .padding(2.dp)
) {
    Box(
        modifier = Modifier
            .background(color = md_theme_dark_surface)
            .fillMaxWidth()
            .requiredHeight(65.dp)
            .background(color = md_theme_dark_surface)
            .clickable {
                onClick()
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            ImageThing(
                model = ImageRequest.Builder(LocalContext.current).data(specificArtwork)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image for unreleased song from a warrior:)",
                modifier = imgModifier, onError = painterResource(randomLostInternetImg())
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
                    color = md_theme_dark_onSurface,
                    modifier = titleModifier
                )
                Text(
                    text = "Lyrics",
                    modifier = lyricsModifier,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = md_theme_light_onSurface
                )
            }
        }

    }
}

@Composable
fun ArtBoard(
    imgURL: String,
    @SuppressLint("ModifierParameter") imgModifier: Modifier = Modifier
        .size(150.dp)
        .shadow(2.dp)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        md_theme_dark_onTertiary, md_theme_dark_surface
                    )
                )
            )
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {

        ImageThing(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgURL)
                .crossfade(true).build(),
            contentDescription = "Image",
            modifier = imgModifier.align(Alignment.Center),
            onError = painterResource(
                R.drawable.image
            )
        )

    }
}
