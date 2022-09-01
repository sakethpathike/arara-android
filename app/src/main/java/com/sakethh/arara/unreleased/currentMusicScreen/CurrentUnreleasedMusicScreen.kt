package com.sakethh.arara.unreleased.currentMusicScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.unreleased.ImageThing

@SuppressLint("ResourceType")
@Preview
@Composable
fun CurrentMusicScreen(
    currentSongTitle: String = "Wake Up",
    currentMusicImg: String = ""
) {
    MaterialTheme(typography = Typography) {
        val context = LocalContext.current
        val currentMusicScreenViewModel: CurrentMusicScreenViewModel = viewModel()
        val constraintsSet = ConstraintSet {
            val artWork = createRefFor("artWork")
            val titleAndIcon = createRefFor("titleAndIcon")
            val topSpaceHeader = createRefFor("topSpaceHeader")
            val progressBarSpacer = createRefFor("progressBarSpacer")
            val progressBar = createRefFor("progressBar")
            val spacerAfterProgressBar = createRefFor("spacerAfterProgressBar")
            val mediaController = createRefFor("mediaController")
            constrain(topSpaceHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(artWork) {
                top.linkTo(topSpaceHeader.bottom)
                start.linkTo(topSpaceHeader.start)
                end.linkTo(topSpaceHeader.end)
            }
            constrain(titleAndIcon) {
                top.linkTo(artWork.bottom)
                start.linkTo(artWork.start)
                end.linkTo(artWork.end)
            }
            constrain(progressBarSpacer) {
                top.linkTo(titleAndIcon.bottom)
                start.linkTo(titleAndIcon.start)
                end.linkTo(titleAndIcon.end)
            }
            constrain(progressBar) {
                top.linkTo(progressBarSpacer.bottom)
                start.linkTo(progressBarSpacer.start)
                end.linkTo(progressBarSpacer.end)
            }
            constrain(spacerAfterProgressBar) {
                top.linkTo(progressBar.bottom)
                start.linkTo(progressBar.start)
                end.linkTo(progressBar.end)
            }
            constrain(mediaController) {
                top.linkTo(spacerAfterProgressBar.bottom)
                start.linkTo(spacerAfterProgressBar.start)
                end.linkTo(spacerAfterProgressBar.end)
            }

        }
        ConstraintLayout(
            constraintSet = constraintsSet,
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .layoutId("topSpaceHeader")
            )
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
                    .layoutId("progressBarSpacer")
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(300.dp)
                    .height(5.dp)
                    .layoutId("progressBar")
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .layoutId("spacerAfterProgressBar")
            )
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .layoutId("mediaController")
            ) {
                val currentIconState = currentMusicScreenViewModel.currentIsPauseIcon
                val currentPlayPauseIcons = currentMusicScreenViewModel.currentPlayPauseIcons
                val currentImageState = remember { mutableListOf(0, 1) }
                if (currentIconState.value) {
                    val playIcon = currentPlayPauseIcons[0]
                    currentImageState[0] = playIcon
                } else {
                    val pauseIcon = currentPlayPauseIcons[1]
                    currentImageState[0] = pauseIcon
                }
                IconButton(
                    onClick = {
                        currentIconState.value = !currentIconState.value
                    }, modifier = Modifier
                        .requiredSize(35.dp)
                        .border(shape = CircleShape,width=1.dp,color=Color.Transparent)
                        .background(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = currentImageState[0]),
                        modifier = Modifier
                            .requiredSize(35.dp),
                        contentDescription = "Play/Pause Icons"
                    )
                }
            }
        }
    }
}