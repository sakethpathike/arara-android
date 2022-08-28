package com.sakethh.arara.unreleased

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.secondGradient

@Preview
@Composable
fun CurrentMusicScreen(currentSongTitle: String = "Wake Up", currentMusicImg: String = "") {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        firstGradient,
                        secondGradient
                    )
                )
            )
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        ImageThing(
            model = ImageRequest.Builder(LocalContext.current)
                .data(currentMusicImg)
                .crossfade(true)
                .build(),
            contentDescription = "Image Of Current Music Which is Playing",
            modifier = Modifier.requiredSize(250.dp),
            onError = painterResource(id = R.drawable.current_img_testing)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.LightGray)
        ) {
            Text(
                text = currentSongTitle,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Description",
                modifier = Modifier.size(35.dp),
                alignment = Alignment.CenterEnd
            )
        }
    }
}