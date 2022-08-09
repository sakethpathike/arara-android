package com.sakethh.arara

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.decode.Decoder
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.transform.Transformation
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.unreleased.ImageThing
import kotlinx.coroutines.*

@Composable
fun FooterThing(decoder: Decoder.Factory) {
        ImageThing (
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://ia802501.us.archive.org/9/items/aurora-artworks/cl31a8pbe00cil9qq5adohc90-footer_a%402x%20%281%29.gif")
                .decoderFactory(decoder)
                .crossfade(true).build(),
            contentDescription = "Khatam!",
            modifier = Modifier
                .fillMaxWidth().height(70.dp),
            onError = painterResource(R.drawable.image)
        )
}

@Composable
fun FooterGIF() {
        if (SDK_INT >= 28) {
            FooterThing(decoder = ImageDecoderDecoder.Factory())
        } else {
            FooterThing(decoder = GifDecoder.Factory())
        }
}
