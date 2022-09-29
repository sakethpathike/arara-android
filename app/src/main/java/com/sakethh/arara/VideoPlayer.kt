package com.sakethh.arara

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(videoUrl:String) {
    val context= LocalContext.current

    val exoPlayer= remember {
        ExoPlayer.Builder(context).build().apply {
            val defaultDataSourceFactory=DefaultDataSource.Factory(context)
            val dataSourceFactory=DefaultDataSource.Factory(context,defaultDataSourceFactory)
            val source=ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(videoUrl))
            setMediaSource(source)
            prepare()
        }
    }
    exoPlayer.playWhenReady=true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
    exoPlayer.repeatMode=Player.REPEAT_MODE_ONE

    DisposableEffect(AndroidView(factory = {
        PlayerView(context).apply {
            hideController()
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            player=exoPlayer
            layoutParams=FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT)
        }
    })){
        onDispose { exoPlayer.release() }
    }
}