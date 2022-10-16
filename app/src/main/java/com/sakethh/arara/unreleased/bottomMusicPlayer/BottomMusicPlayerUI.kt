package com.sakethh.arara.unreleased.bottomMusicPlayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.sakethh.arara.unreleased.SharedViewModel
import com.sakethh.arara.unreleased.UnreleasedScreenCurrentData
import com.sakethh.arara.unreleased.UnreleasedViewModel


@Composable
fun BottomMusicPlayerUIComposable(
    songName: String,
    imgUrl: String,
    onClick:()->Unit,
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
            )
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
                    .clickable { onClick.invoke() }, onError = painterResource(randomLostInternetImg())
            )
        }
        Spacer(modifier = Modifier
            .width(8.dp)
            .clickable { onClick.invoke() })
        Box(
            contentAlignment = Alignment.CenterStart, modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .clickable { onClick.invoke() }
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
                val musicControlBoolean = remember { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControl}
                Box(modifier = Modifier.size(20.dp)) {
                    if (!musicControlBoolean.value) {
                        GIFThing(
                            imgURL = UnreleasedViewModel.MediaPlayer.currentLoadingStatusGIFURL.value, modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
        val unreleasedViewModel: UnreleasedViewModel = viewModel()
        val musicControlBoolean = remember { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControl}
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(10.dp)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.CenterEnd,
        ) {
           if(UnreleasedViewModel.MediaPlayer.musicPlayerVisibility.value){
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
fun BottomMusicPlayerUI(animatedNavController:NavHostController, sharedViewModel: SharedViewModel) {
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val unreleasedSongNameForPlayer =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerTitle }
    val unreleasedImgURLForPlayer =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerImgURL }
    val unreleasedLyricsForPlayer =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerLyrics }
    val musicControlBoolean = rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControl }
    val rememberMusicPlayerControlImg =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerControlImg }
    val currentControlIcon = rememberSaveable { mutableListOf(0, 1) }
    val rememberMusicPlayerDescription =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescription }
    val rememberMusicPlayerDescriptionBy =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescriptionBy }
    val rememberMusicPlayerDescriptionOrigin =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.rememberMusicPlayerDescriptionOrigin }
    val rememberMusicPlayerArtworkBy = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerArtworkBy
    val currentSongMaxDuration = rememberSaveable { UnreleasedViewModel.MediaPlayer.currentSongMaxDuration }
    val currentSongCurrentDuration =
        rememberSaveable { UnreleasedViewModel.MediaPlayer.currentSongCurrentDuration }
    val currentSongIsPlaying = rememberSaveable { UnreleasedViewModel.MediaPlayer.currentSongIsPlaying }
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
                currentImgUrl = UnreleasedViewModel.MediaPlayer.rememberMusicPlayerHDImgURL.value,
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
            if (UnreleasedViewModel.MediaPlayer.mediaPlayer.isPlaying) {
                UnreleasedViewModel.MediaPlayer.mediaPlayer.pause()
            } else {
                UnreleasedViewModel.MediaPlayer.mediaPlayer.start()
            }
        },
        onControlClickImg = currentControlIcon[0]
    )
}