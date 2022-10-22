package com.sakethh.arara.unreleased.bottomMusicPlayer

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.request.ImageRequest
import com.sakethh.arara.GIFThing
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import com.sakethh.arara.SharedViewModel
import com.sakethh.arara.unreleased.UnreleasedScreenCurrentData
import com.sakethh.arara.unreleased.UnreleasedViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomMusicPlayerUIComposable(
    songName: String,
    imgUrl: String,
    onClick: () -> Unit,
    onControlClick: () -> Unit = {},
    onControlClickImg: Int
) {
    val paddingValue = 20.dp
    Row(
        modifier = Modifier
            .padding(start = paddingValue, end = paddingValue)
            .requiredHeight(65.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(5.dp),
                color = md_theme_dark_secondary
            ).bottomMusicPlayerOnClick { onClick.invoke() }
            .background(color = (md_theme_dark_onSecondary))
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            ImageThing(
                model = ImageRequest.Builder(LocalContext.current).data(imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image for unreleased song from a warrior:)",
                modifier = Modifier//gives 10dp padding in top
                    .requiredHeight(45.dp) // renders height of the image
                    .padding(start = 10.dp) //gives 10dp padding in left
                    .requiredWidth(45.dp) //renders width of the image
                    .bottomMusicPlayerOnClick { onClick.invoke() },
                onError = painterResource(randomLostInternetImg())
            )
        }
        Spacer(modifier = Modifier
            .width(8.dp)
            .bottomMusicPlayerOnClick { onClick.invoke() })
        Box(
            contentAlignment = Alignment.CenterStart, modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .bottomMusicPlayerOnClick { onClick.invoke() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = songName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = md_theme_dark_onSurface,
                )
                Spacer(modifier = Modifier.height(3.dp))
                val unreleasedViewModel: UnreleasedViewModel = viewModel()
                val musicControlBoolean =
                    remember { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControl }
                Box(modifier = Modifier.size(20.dp)) {
                    if (!musicControlBoolean.value) {
                        GIFThing(
                            imgURL = UnreleasedViewModel.UnreleasedUtils.currentLoadingStatusGIFURL.value,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
        val unreleasedViewModel: UnreleasedViewModel = viewModel()
        val musicControlBoolean =
            remember { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControl }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(10.dp)
                .bottomMusicPlayerOnClick { onClick.invoke() },
            contentAlignment = Alignment.CenterEnd,
        ) {
            if (UnreleasedViewModel.UnreleasedUtils.musicPlayerVisibility.value) {
                Image(  // play || pause button exists
                    painter = painterResource(id = onControlClickImg),
                    modifier = Modifier
                        .requiredSize(35.dp)
                        .clickable {
                            onControlClick()
                            musicControlBoolean.value = !musicControlBoolean.value
                        },
                    contentDescription = "Play/Pause Icons"
                )
            }
        }
    }
}

@Composable
fun BottomMusicPlayerUI(
    animatedNavController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedSongNameForPlayer =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerTitle }
    val unreleasedImgURLForPlayer =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerImgURL }
    val unreleasedLyricsForPlayer =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerLyrics }
    val musicControlBoolean =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerControlImg }
    val currentControlIcon = rememberSaveable { mutableListOf(0, 1) }
    val rememberMusicPlayerDescription =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescription }
    val rememberMusicPlayerDescriptionBy =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescriptionBy }
    val rememberMusicPlayerDescriptionOrigin =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerDescriptionOrigin }
    val rememberMusicPlayerArtworkBy =
        UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerArtworkBy
    val currentSongMaxDuration =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentSongMaxDuration }
    val currentSongCurrentDuration =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentSongCurrentDuration }
    val currentSongIsPlaying =
        rememberSaveable { UnreleasedViewModel.UnreleasedUtils.currentSongIsPlaying }
    if (musicControlBoolean.value) {
        val playIcon = rememberMusicPlayerControlImg[0]  //play icon
        currentControlIcon[0] = playIcon
    } else {
        val pauseIcon = rememberMusicPlayerControlImg[1] //pause icon
        currentControlIcon[0] = pauseIcon
    }
    BottomMusicPlayerUIComposable(
        songName = unreleasedSongNameForPlayer.value,
        imgUrl = unreleasedImgURLForPlayer.value,
        onClick = {
            val dataForCurrentMusicScreen = UnreleasedScreenCurrentData(
                currentSongName = unreleasedSongNameForPlayer.value,
                currentImgUrl = UnreleasedViewModel.UnreleasedUtils.rememberMusicPlayerHDImgURL.value,
                currentLyrics = unreleasedLyricsForPlayer.value,
                songDescription = rememberMusicPlayerDescription.value,
                descriptionBy = rememberMusicPlayerDescriptionBy.value,
                descriptionOrigin = rememberMusicPlayerDescriptionOrigin.value,
                artworkBy = rememberMusicPlayerArtworkBy.value,
                currentSongMaxDuration = unreleasedViewModel.musicDuration(
                    currentSongMaxDuration.value.toLong()
                ).value,
                currentDuration = unreleasedViewModel.musicDuration(
                    currentSongCurrentDuration.value.toLong()
                ).value,
                currentDurationFloat = currentSongCurrentDuration.value.toFloat(),
                isPlaying = currentSongIsPlaying.value
            )
            sharedViewModel.data(data = dataForCurrentMusicScreen)
            animatedNavController.navigate("currentPlayingUnreleasedMusicScreen")

        },
        onControlClick = {
            if (UnreleasedViewModel.UnreleasedUtils.mediaPlayer.isPlaying) {
                UnreleasedViewModel.UnreleasedUtils.mediaPlayer.pause()
            } else {
                UnreleasedViewModel.UnreleasedUtils.mediaPlayer.start()
            }
        },
        onControlClickImg = currentControlIcon[0]
    )
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bottomMusicPlayerOnClick(onClick: () -> Unit): Modifier = composed{
    val context = LocalContext.current
    this.combinedClickable(onLongClick = {
        UnreleasedViewModel.UnreleasedUtils.mediaPlayer.stop()
        UnreleasedViewModel.UnreleasedUtils.mediaPlayer.reset()
        UnreleasedViewModel.UnreleasedUtils.musicPlayerActivate.value = !UnreleasedViewModel.UnreleasedUtils.musicPlayerActivate.value
        UnreleasedViewModel.UnreleasedUtils.musicCompleted.value = !UnreleasedViewModel.UnreleasedUtils.musicCompleted.value
        Toast.makeText(context,"Closed bottom music player!",Toast.LENGTH_SHORT).show()
    }, onClick = {
        onClick.invoke()
    })
}
