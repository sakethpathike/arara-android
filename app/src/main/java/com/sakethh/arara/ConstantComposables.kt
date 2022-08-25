package com.sakethh.arara

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.decode.Decoder
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.unreleased.ImageThing
import com.sakethh.arara.unreleased.UnreleasedViewModel

@Composable
fun GIFThing(imgURL:String, modifier:Modifier) {
        ImageThing (
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgURL)
                .decoderFactory(GifDecoder.Factory())
                .crossfade(true).build(),
            contentDescription = "Khatam!",
            modifier = modifier,
            onError = painterResource(randomLostInternetImg())
        )
}
