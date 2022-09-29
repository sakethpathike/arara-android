package com.sakethh.arara.home.selectedChipScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing

@Composable
fun SelectedChipComposable(currentIterator:Int) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(400.dp), colors = CardDefaults.cardColors(containerColor = md_theme_dark_onPrimary)
    ) {
        ImageThing(
            model = ImageRequest.Builder(context).data("").crossfade(true).build(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            onError = painterResource(id = randomLostInternetImg())
        )
        Text(
            text = "Hello, hwo are you im fine wht about yo what are you doing im doping welll blablabla",
            color = md_theme_dark_primary,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            lineHeight = 22.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
        )
        Text(
            text = "Art by :- blablablabablalbalblabl$currentIterator",
            color = md_theme_dark_primary,
            fontSize = 10.sp,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            lineHeight = 10.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
        )
    }


}