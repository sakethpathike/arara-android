package com.sakethh.arara.unreleased.currentMusicScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
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
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.UnreleasedViewModel.UnreleasedUtils.mediaPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnreleasedCurrentMusicScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    scaffoldState: BottomSheetScaffoldState
) {
    BackHandler {
        sharedViewModel.isBottomNavVisible.value = true
        navController.navigate("unreleased") {
            popUpTo(0)
        }
    }
    sharedViewModel.isBottomNavVisible.value = false
    val systemUIController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()
    val currentMusicScreenViewModel: CurrentMusicScreenViewModel = viewModel()
    LaunchedEffect(key1 = coroutineScope) {
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
            constrain(lyricsBox) {
                top.linkTo(progressBarBox.bottom)
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
                    .height(5.dp)
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
                    if (currentMusicScreenViewModel.isMusicPlaying().collectAsState(initial = false).value as Boolean && !scaffoldState.bottomSheetState.isCollapsed) {
                        Slider(
                            value = currentMusicScreenViewModel.currentDurationFloatFlow().collectAsState(initial = 0f).value.toString()
                                .toFloat(),
                            onValueChange = {
                                mediaPlayer.seekTo(it.toInt())
                            },
                            valueRange = 0f..mediaPlayer.duration.toFloat(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .layoutId("progressBar"),
                            colors = SliderDefaults.colors(
                                thumbColor = md_theme_dark_onSurface,
                                activeTrackColor = md_theme_dark_onSurface,
                                inactiveTrackColor = md_theme_dark_secondary
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = currentMusicScreenViewModel.currentDurationFlow().collectAsState(initial = "00:00").value.toString(),
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
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = currentMusicScreenViewModel.actualDuration().collectAsState(initial = "00:00").value.toString(),
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
            // lyrics thing:
            Box(
                modifier = Modifier
                    .padding(
                        top = 35.dp,
                        bottom = 0.dp,
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