package com.sakethh.arara

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.sakethh.arara.unreleased.ImageThing

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
