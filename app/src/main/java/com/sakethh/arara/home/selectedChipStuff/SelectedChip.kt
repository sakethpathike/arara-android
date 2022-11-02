@file:Suppress("LocalVariableName")

package com.sakethh.arara.home.selectedChipStuff

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.FileUtils
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.sakethh.arara.R
import com.sakethh.arara.SharedViewModel
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.randomLostInternetImg
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import java.io.File
import java.net.URI
import java.net.URL

@SuppressLint("ResourceAsColor")
@Composable
fun SelectedChipComposable(
    navController: NavController,
    imgLink: String,
    title: String,
    author: String,
    permalink: String,
    index: Int,
    indexedValue: Int,
    indexOnClick: (Int) -> Unit,
    bookMarkText: String = "Bookmark",
    bookMarkIcon: Int = R.drawable.bookmark_icon,
    inBookMarkScreen: Boolean = false,
    bookmarkOnClick: (() -> Unit)? = null,
    sharedViewModel: SharedViewModel,
    bookMarkRedirect : () -> Unit = {}
) {
    val context = LocalContext.current
    val view = LocalView.current
    val _index = remember { mutableStateOf(index) }
    val coroutineScope = rememberCoroutineScope()
    val selectedChipScreenViewModel: SelectedChipScreenViewModel = viewModel()
    val imageIsLoading = remember { selectedChipScreenViewModel.imageIsLoading }
    val dropDownMenuEnabled = remember { mutableStateOf(false) }
    val constraintSet = ConstraintSet {
        val titleForCard = createRefFor("titleForCard")
        val descriptionIcon = createRefFor("descriptionIcon")
        val dropDownIcon = createRefFor("dropDownIcon")
        val dropDownComposable = createRefFor("dropDownComposable")
        val image = createRefFor("image")
        constrain(titleForCard) {
            top.linkTo(image.bottom)
            start.linkTo(parent.start)
        }
        constrain(descriptionIcon) {
            top.linkTo(image.bottom)
            end.linkTo(parent.end)
        }
        constrain(dropDownIcon) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(dropDownComposable) {
            top.linkTo(dropDownIcon.bottom)
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
        ConstraintLayout(
            constraintSet = constraintSet,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize()
        ) {
            ImageThing(
                model = ImageRequest.Builder(context).data(imgLink)
                    .crossfade(true).build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .layoutId("image")
                    .clickable {
                        if(inBookMarkScreen){
                            bookMarkRedirect()
                        }else{
                            sharedViewModel.assignPermalink(permalink = "https://www.reddit.com/$permalink")
                            navController.navigate("webView")
                        }
                    },
                onError = painterResource(id = randomLostInternetImg()),
                contentScale = ContentScale.Crop,
                onLoading = { imageIsLoading.value = true },
                onSuccess = { imageIsLoading.value = false }
            )
            IconButton(onClick = {
                dropDownMenuEnabled.value = !dropDownMenuEnabled.value
            }, modifier = Modifier.layoutId("dropDownIcon")) {
                Image(
                    painter = painterResource(id = R.drawable.more_icon),
                    contentDescription = "more_icon",
                    modifier = Modifier
                        .size(24.dp)
                        .shadow(24.dp)
                )
            }
            Box(
                modifier = Modifier
                    .layoutId("dropDownComposable")
                    .padding(end = 10.dp)
                    .wrapContentSize()
            ) {
                DropdownMenu(
                    modifier = Modifier
                        .background(md_theme_dark_onTertiary),
                    expanded = dropDownMenuEnabled.value,
                    onDismissRequest = { dropDownMenuEnabled.value = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = bookMarkText,
                                style = MaterialTheme.typography.bodySmall,
                                color = md_theme_dark_tertiary
                            )
                        },
                        onClick = {
                            if (inBookMarkScreen) {
                                bookmarkOnClick?.invoke()
                            } else {
                                SelectedChipScreenViewModel.BookMarkedDataUtils.realmDBObject.apply {
                                    this.objectKey = imgLink
                                    this.imgURL = imgLink
                                    this.bookMarked = true
                                    this.title = title
                                    this.author = author
                                    this.permalink = "https://www.reddit.com/$permalink"
                                }.also {
                                    selectedChipScreenViewModel.addToDB()
                                }
                                Toast.makeText(
                                    context,
                                    SelectedChipScreenViewModel.BookMarkedDataUtils.toastMessage.value,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            dropDownMenuEnabled.value = false
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = bookMarkIcon),
                                contentDescription = "bookmark_icon",
                                modifier = Modifier.size(24.dp)
                            )
                        })
                    DropdownMenuItem(text = {
                        Text(
                            text = "Share",
                            style = MaterialTheme.typography.bodySmall,
                            color = md_theme_dark_tertiary
                        )
                    }, onClick = {
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.type = "text/plain"
                        intent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Yo! Sharing \"$title\" by \"$author\" from reddit; img url :- \"$imgLink\""
                        )
                        val shareIntent = Intent.createChooser(intent, "Share using :-")
                        context.startActivity(shareIntent)
                        dropDownMenuEnabled.value = false
                    }, leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.share_icon),
                            contentDescription = "share_icon",
                            modifier = Modifier.size(24.dp)
                        )
                    })

                }
            }
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
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 30.dp)
                        .layoutId("titleForCard")
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
