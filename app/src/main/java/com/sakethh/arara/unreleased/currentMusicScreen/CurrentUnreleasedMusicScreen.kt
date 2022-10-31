package com.sakethh.arara.unreleased.currentMusicScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.R
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import com.sakethh.arara.SharedViewModel
import com.sakethh.arara.unreleased.UnreleasedViewModel.UnreleasedUtils.mediaPlayer
import com.sakethh.arara.unreleased.currentMusicScreen.CurrentMusicScreenViewModel.CurrentMusicScreenUtils.actualDuration
import com.sakethh.arara.unreleased.currentMusicScreen.CurrentMusicScreenViewModel.CurrentMusicScreenUtils.currentDurationFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnreleasedCurrentMusicScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController
) {
    BackHandler {
        sharedViewModel.isBottomNavVisible.value=true
        navController.navigate("unreleased"){
            popUpTo(0)
        }
    }
    sharedViewModel.isBottomNavVisible.value=false
    val systemUIController=rememberSystemUiController()
    val coroutineScope= rememberCoroutineScope()
    val currentMusicScreenViewModel: CurrentMusicScreenViewModel = viewModel()
    val currentDuration = currentDurationFlow().collectAsState(initial = "00:00").value
    val actualDuration=   actualDuration().collectAsState(initial = "00:00").value
    LaunchedEffect(key1 = coroutineScope){
        coroutineScope.launch {
            delay(100)
            systemUIController.setStatusBarColor(color = md_theme_dark_surface)
        }
    }
    MaterialTheme(typography = Typography) {
        val startAndEndPadding = 20.dp
        val constraintsSet = ConstraintSet {
            val topBar = createRefFor("topBar")
            val artWork = createRefFor("artWork")
            val titleAndIconAndDescription = createRefFor("titleAndIconAndDescription")
            val titleAndIcon = createRefFor("titleAndIcon")
            val descriptionText = createRefFor("descriptionText")
            val topSpaceHeader = createRefFor("topSpaceHeader")
            val progressBarSpacer = createRefFor("progressBarSpacer")
            val progressBarBox = createRefFor("progressBarBox")
            val progressBar = createRefFor("progressBar")
            val boxAfterProgressBar = createRefFor("boxAfterProgressBar")
            val mediaControllerPlayPauseButton = createRefFor("mediaControllerPlayPauseButton")
            val mediaControllerPreviousButton = createRefFor("mediaControllerPreviousButton")
            val mediaControllerNextButton = createRefFor("mediaControllerNextButton")
            val lyricsBox = createRefFor("lyricsBox")
            constrain(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(topSpaceHeader) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(artWork) {
                top.linkTo(topSpaceHeader.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(titleAndIconAndDescription.top)
            }
            constrain(titleAndIconAndDescription) {
                top.linkTo(artWork.bottom)
                bottom.linkTo(lyricsBox.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(titleAndIcon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(descriptionText) {
                top.linkTo(titleAndIcon.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            constrain(progressBarSpacer) {
                top.linkTo(titleAndIconAndDescription.bottom)
                start.linkTo(titleAndIconAndDescription.start)
                end.linkTo(titleAndIconAndDescription.end)
            }
            constrain(progressBarBox) {
                top.linkTo(progressBarSpacer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(progressBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(progressBarBox) {
                top.linkTo(progressBarSpacer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(boxAfterProgressBar) {
                top.linkTo(progressBarBox.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(mediaControllerPlayPauseButton) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(mediaControllerPreviousButton) {
                top.linkTo(mediaControllerPlayPauseButton.top)
                bottom.linkTo(mediaControllerPlayPauseButton.bottom)
                end.linkTo(mediaControllerPlayPauseButton.start)
            }
            constrain(mediaControllerNextButton) {
                top.linkTo(mediaControllerPlayPauseButton.top)
                bottom.linkTo(mediaControllerPlayPauseButton.bottom)
                start.linkTo(mediaControllerPlayPauseButton.end)
            }
            constrain(lyricsBox) {
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
                    .background(md_theme_dark_surface)
                    .verticalScroll(state = rememberScrollState(), enabled = true)
                    .animateContentSize()
                    .padding(bottom = 150.dp)
            ) {
                SmallTopAppBar(
                    modifier = Modifier.layoutId("topBar"),
                    title = {},
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate("unreleased")
                            }, modifier = Modifier
                                .padding(start = 15.dp)
                                .size(30.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.arrow_right),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .rotate(180f)
                            )
                        }
                    }, actions = {
                        val image = remember { mutableListOf(0) }
                        val mediaIcons = currentMusicScreenViewModel.currentImageGifIcons
                        if (currentMusicScreenViewModel.currentIsImageIcon.value) {
                            image[0] = mediaIcons[0]
                        } else {
                            image[0] = mediaIcons[1]
                        }
                        IconButton(
                            onClick = {
                                currentMusicScreenViewModel.currentIsImageIcon.value =
                                    !currentMusicScreenViewModel.currentIsImageIcon.value
                            }
                        ) {
                            Image(
                                painter = painterResource(id = image[0]),
                                contentDescription = "gif/image can be  selected from here",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                )
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .layoutId("topSpaceHeader")
                )

                ImageThing(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(sharedViewModel.dataForCurrentMusicScreen.value?.currentImgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image Of Current Music Which is Playing",
                    modifier = Modifier
                        .requiredSize(300.dp)
                        .layoutId("artWork")
                        .shadow(2.dp),
                    onError = painterResource(id = randomLostInternetImg())
                )
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(start = startAndEndPadding, end = startAndEndPadding)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .layoutId("titleAndIconAndDescription")
                        .animateContentSize()
                ) {
                    ConstraintLayout(constraintSet = constraintsSet) {
                        Row(
                            modifier = Modifier
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
                                    text = sharedViewModel.dataForCurrentMusicScreen.value?.currentSongName!!,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = md_theme_dark_onSurface,
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
                                IconButton(onClick = {
                                    currentMusicScreenViewModel.descriptionButtonClicked.value =
                                        !currentMusicScreenViewModel.descriptionButtonClicked.value
                                }, modifier = Modifier.size(25.dp)) {
                                    if (currentMusicScreenViewModel.descriptionButtonClicked.value) {
                                        Image(
                                            painter = painterResource(id = R.drawable.dropdown),
                                            contentDescription = "Description",
                                            modifier = Modifier
                                                .rotate(180f)
                                                .animateContentSize()
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.dropdown),
                                            contentDescription = "Description"
                                        )
                                    }
                                }
                            }
                        }
                        if (currentMusicScreenViewModel.descriptionButtonClicked.value) {
                            Text(
                                text = "${sharedViewModel.dataForCurrentMusicScreen.value?.songDescription}\n\nDescription Via:- ${sharedViewModel.dataForCurrentMusicScreen.value?.descriptionBy} from ${sharedViewModel.dataForCurrentMusicScreen.value?.descriptionOrigin}.\nArtwork stolen from ${sharedViewModel.dataForCurrentMusicScreen.value?.artworkBy}:)",
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                color = md_theme_dark_onSurface,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .layoutId("descriptionText")
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .layoutId("progressBarSpacer")
                )
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(start = startAndEndPadding, end = startAndEndPadding, top = 5.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .animateContentSize()
                        .layoutId("progressBarBox")
                ) {
                    ConstraintLayout(
                        constraintSet = constraintsSet, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                            var sliderPosition =
                                sharedViewModel.dataForCurrentMusicScreen.value
                            Slider(
                                value = 400f, onValueChange = {}, valueRange = 0f..400f, modifier = Modifier
                                    .padding(top = 9.dp)
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .layoutId("progressBar")
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = currentDuration.toString(),
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = md_theme_dark_onSurface,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .padding(top = 26.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = actualDuration.toString(),
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = md_theme_dark_onSurface,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .padding(top = 26.dp)
                                )
                            }

                    }
                }

                val currentIconState = currentMusicScreenViewModel.currentIsPauseIcon
                val currentPlayPauseIcons = currentMusicScreenViewModel.currentPlayPauseIcons
                val currentImageState = remember { mutableListOf(0, 1) }
                if (currentMusicScreenViewModel.isCurrentIconPause.value) {
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
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), constraintSet = constraintsSet
                    ) {
                        IconButton(modifier = Modifier
                            .layoutId("mediaControllerPreviousButton"), onClick = {}) {
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
                                if (currentIconState.value) {
                                    mediaPlayer.start()
                                } else {
                                    mediaPlayer.pause()
                                }
                            },
                            modifier = Modifier
                                .requiredSize(50.dp)
                                .background(color = md_theme_dark_onSurface, shape = CircleShape)
                                .layoutId("mediaControllerPlayPauseButton"),
                        ) {
                            Image(
                                painter = painterResource(id = currentImageState[0]),
                                modifier = Modifier
                                    .requiredSize(30.dp),
                                contentDescription = "Play/Pause Icons"
                            )
                        }
                        IconButton(
                            onClick = { },
                            modifier = Modifier.layoutId("mediaControllerNextButton")
                        ) {
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
                Box(
                    modifier = Modifier
                        .padding(
                            top = 35.dp,
                            bottom = startAndEndPadding,
                            start = startAndEndPadding,
                            end = startAndEndPadding
                        )
                        .fillMaxWidth()
                        .height(350.dp)
                        .background(md_theme_dark_onSecondary, shape = RoundedCornerShape(10.dp))
                        .layoutId("lyricsBox")
                ) {
                    Text(
                        modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                        text = "LYRICS",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleMedium,
                        color = md_theme_dark_secondary
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 25.dp, bottom = 20.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 45.dp)
                            .verticalScroll(
                                rememberScrollState()
                            )
                    ) {

                        Text(
                            fontSize = 25.sp,
                            text = sharedViewModel.dataForCurrentMusicScreen.value?.currentLyrics!!.toString(),
                            textAlign = TextAlign.Start,
                            lineHeight = 30.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = md_theme_dark_secondary
                        )
                    }

                }
            }
        }
    }