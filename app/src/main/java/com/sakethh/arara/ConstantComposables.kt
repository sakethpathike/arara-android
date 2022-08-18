package com.sakethh.arara

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
fun FooterThing(decoder: Decoder.Factory,imgURL:String) {
        ImageThing (
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgURL)
                .decoderFactory(decoder)
                .crossfade(true).build(),
            contentDescription = "Khatam!",
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(backgroundColor),
            onError = painterResource(R.drawable.image)
        )
}

@Composable
fun FooterGIF(imgURL: String) {
        if (SDK_INT >= 28) {
            FooterThing(decoder = ImageDecoderDecoder.Factory(),imgURL)
        } else {
            FooterThing(decoder = GifDecoder.Factory(),imgURL)
        }
}
