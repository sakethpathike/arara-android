package com.sakethh.arara.unreleased.currentMusicScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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


@Composable
fun UnreleasedCurrentMusicScreen(
    currentSongTitle: String,
    currentMusicImg: String,
    currentSongLyrics:String
) {
    MaterialTheme(typography = Typography) {
       val startAndEndPadding=20.dp
        val currentMusicScreenViewModel: CurrentMusicScreenViewModel = viewModel()
        val constraintsSet = ConstraintSet {
            val artWork = createRefFor("artWork")
            val titleAndIcon = createRefFor("titleAndIcon")
            val topSpaceHeader = createRefFor("topSpaceHeader")
            val progressBarSpacer = createRefFor("progressBarSpacer")
            val progressBar = createRefFor("progressBar")
            val boxAfterProgressBar = createRefFor("boxAfterProgressBar")
            val mediaControllerPlayPauseButton = createRefFor("mediaControllerPlayPauseButton")
            val mediaControllerPreviousButton = createRefFor("mediaControllerPreviousButton")
            val mediaControllerNextButton = createRefFor("mediaControllerNextButton")
            val lyricsBox = createRefFor("lyricsBox")
            constrain(topSpaceHeader) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(artWork) {
                top.linkTo(topSpaceHeader.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(titleAndIcon) {
                top.linkTo(artWork.bottom)
                bottom.linkTo(lyricsBox.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(progressBarSpacer) {
                top.linkTo(titleAndIcon.bottom)
                start.linkTo(titleAndIcon.start)
                end.linkTo(titleAndIcon.end)
            }
            constrain(progressBar) {
                top.linkTo(progressBarSpacer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(boxAfterProgressBar){
                top.linkTo(progressBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(mediaControllerPlayPauseButton){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(mediaControllerPreviousButton){
                top.linkTo(mediaControllerPlayPauseButton.top)
                bottom.linkTo(mediaControllerPlayPauseButton.bottom)
                end.linkTo(mediaControllerPlayPauseButton.start)
            }
            constrain(mediaControllerNextButton){
                top.linkTo(mediaControllerPlayPauseButton.top)
                bottom.linkTo(mediaControllerPlayPauseButton.bottom)
                start.linkTo(mediaControllerPlayPauseButton.end)
            }
            constrain(lyricsBox){
                top.linkTo(boxAfterProgressBar.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        }
            ConstraintLayout(
                constraintSet = constraintsSet,
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .verticalScroll(state = rememberScrollState(), enabled = true)
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
                        .padding(start = startAndEndPadding, end = startAndEndPadding)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .layoutId("titleAndIcon")
                ) {
                    Box(
                        modifier = Modifier
                            .requiredWidthIn(min = 250.dp)
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
                            .fillMaxWidth()
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
                        .padding(start = startAndEndPadding, end = startAndEndPadding)
                        .fillMaxWidth()
                        .height(5.dp)
                        .layoutId("progressBar")
                )
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
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(top = 20.dp, start = startAndEndPadding, end = startAndEndPadding)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .layoutId("boxAfterProgressBar")
                ) {
                    ConstraintLayout( modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),constraintSet = constraintsSet ) {
                        IconButton(modifier = Modifier
                            .layoutId("mediaControllerPreviousButton"),onClick={}){
                            Image(
                                painter = painterResource(id = R.drawable.previoustrack),
                                modifier = Modifier
                                    .padding(end = 30.dp)
                                    .requiredSize(35.dp),
                                contentDescription = "PreviousButton"
                            )
                        }
                        IconButton(
                            // play||pause buttons
                            onClick = {
                                currentIconState.value = !currentIconState.value
                            },
                            modifier = Modifier
                                .requiredSize(50.dp)
                                .background(color = Color.White, shape = CircleShape)

                                .layoutId("mediaControllerPlayPauseButton"),
                        ) {
                            Image(
                                painter = painterResource(id = currentImageState[0]),
                                modifier = Modifier
                                    .requiredSize(30.dp),
                                contentDescription = "Play/Pause Icons"
                            )
                        }
                       IconButton(onClick = {  }, modifier = Modifier.layoutId("mediaControllerNextButton")) {
                        Image(
                            painter = painterResource(id = R.drawable.nexttrack),
                            modifier = Modifier
                                .padding(start = 30.dp)
                                .requiredSize(35.dp),
                            contentDescription = "NextButton"
                        )
                       }
                    }
                }
                // lyrics thing:
                Box(modifier = Modifier
                    .padding(
                        top = 35.dp,
                        bottom = startAndEndPadding,
                        start = startAndEndPadding,
                        end = startAndEndPadding
                    )
                    .fillMaxWidth()
                    .height(475.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .layoutId("lyricsBox")) {
                    Text(modifier = Modifier.padding(start=15.dp,top=15.dp), text = "LYRICS", textAlign = TextAlign.Start,style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    Column(modifier = Modifier
                        .padding(start = 15.dp, end = 25.dp, bottom = 20.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 45.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )) {
                        Text( fontSize = 28.sp,text = currentSongLyrics, textAlign = TextAlign.Start,style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    }
                }
            }
        }
    }