package com.sakethh.arara.unreleased

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.request.ImageRequest
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.secondGradient
import com.valentinilk.shimmer.shimmer

@Preview
@Composable
fun CurrentMusicScreen(
    currentSongTitle: String = "Wake Up",
    currentMusicImg: String = ""
) {
    MaterialTheme(typography = Typography) {
        val constraintsSet = ConstraintSet {
            val artWork = createRefFor("artWork")
            val titleAndIcon = createRefFor("titleAndIcon")
            val topHeader = createRefFor("topHeader")
            constrain(topHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(artWork) {
                top.linkTo(topHeader.bottom)
                start.linkTo(topHeader.start)
                end.linkTo(topHeader.end)
            }
            constrain(titleAndIcon) {
                top.linkTo(artWork.bottom)
                start.linkTo(artWork.start)
                end.linkTo(artWork.end)
            }
        }
        ConstraintLayout(
            constraintSet = constraintsSet,
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        ) {
            Spacer(Modifier.fillMaxWidth().height(50.dp).layoutId("topHeader"))
            ImageThing(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(currentMusicImg)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image Of Current Music Which is Playing",
                modifier = Modifier
                    .requiredSize(300.dp)
                    .layoutId("artWork")
                    .shadow(2.dp),
                onError = painterResource(id = R.drawable.current_music_screen_pic)
            )
            Row(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .width(300.dp)
                    .wrapContentHeight()
                    .layoutId("titleAndIcon")
            ) {
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .wrapContentHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = currentSongTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        maxLines = 1,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .wrapContentHeight(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(onClick = { }, modifier = Modifier.size(25.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.dropdown),
                            contentDescription = "Description"
                        )
                    }
                }
            }
        }
    }
}