@file:Suppress("LocalVariableName")

package com.sakethh.arara.home.selectedChipStuff

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.sakethh.arara.R
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing

@Composable
fun SelectedChipComposable(
    imgLink: String,
    title: String,
    author: String,
    index: Int,
    indexedValue: Int,
    indexOnClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val _index = remember { mutableStateOf(index) }
    val selectedChipScreenViewModel: SelectedChipScreenViewModel = viewModel()
    val imageIsLoading = remember { selectedChipScreenViewModel.imageIsLoading }
    val constraintSet = ConstraintSet {
        val titleForCard = createRefFor("titleForCard")
        val descriptionIcon = createRefFor("descriptionIcon")
        constrain(titleForCard) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(descriptionIcon) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
    }
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = md_theme_dark_onPrimary)
    ) {
        ImageThing(
            model = ImageRequest.Builder(context).data(imgLink)
                .crossfade(true).build(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .placeholder(
                    visible = false,
                    color = md_theme_dark_onTertiary,
                    highlight = PlaceholderHighlight.fade(highlightColor = md_theme_dark_primaryContainer)
                ),
            onError = painterResource(id = randomLostInternetImg()),
            contentScale = ContentScale.Crop,
            onLoading = { imageIsLoading.value = true },
            onSuccess = { imageIsLoading.value = false }
        )
        ConstraintLayout(
            constraintSet = constraintSet,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize()
        ) {
            if (_index.value != indexedValue) {
                Text(
                    text = title,
                    color = md_theme_dark_primary,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 30.dp)
                )
            } else {
                Text(
                    text = title,
                    color = md_theme_dark_primary,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    lineHeight = 22.sp,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 25.dp)
                        .layoutId("titleForCard")
                )
            }
            IconButton(
                modifier = Modifier.layoutId("descriptionIcon"),
                onClick = {
                    if (_index.value != indexedValue) {
                        indexOnClick(index)
                    } else {
                        HomeScreenViewModel.Utils.nonIndexedValue.value = -70
                    }
                }
            ) {
                val modifierForDropDown = if (_index.value != indexedValue) {
                    Modifier
                        .requiredSize(24.dp)
                } else {
                    Modifier
                        .requiredSize(24.dp)
                        .rotate(180f)
                }
                Image(
                    painter = painterResource(id = R.drawable.dropdown_homescreen),
                    contentDescription = "dropdown_homescreen",
                    modifier = modifierForDropDown
                )
            }
        }
        Text(
            text = author,
            color = md_theme_dark_primary,
            fontSize = 10.sp,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            lineHeight = 10.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        )

    }
}