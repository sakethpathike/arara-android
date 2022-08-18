package com.sakethh.arara

import android.os.Build.VERSION.SDK_INT
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
import com.sakethh.arara.unreleased.ImageThing
import com.sakethh.arara.unreleased.UnreleasedViewModel

@Composable
fun FooterThing(decoder: Decoder.Factory) {
    val viewModel:UnreleasedViewModel= viewModel()
    val data=viewModel.rememberUnreleasedFooterImg.value[0]
        ImageThing (
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.footerImg)
                .decoderFactory(decoder)
                .crossfade(true).build(),
            contentDescription = "Khatam!",
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
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
