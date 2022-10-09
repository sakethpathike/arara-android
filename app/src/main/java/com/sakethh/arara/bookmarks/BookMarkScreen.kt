package com.sakethh.arara.bookmarks

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sakethh.arara.R
import com.sakethh.arara.RealmDBObject
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.home.selectedChipStuff.SelectedChipScreenViewModel
import com.sakethh.arara.ui.theme.md_theme_dark_surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkScreen() {
    val bookmarkScreenViewModel: BookMarkScreenViewModel = viewModel()
    val savedData: List<RealmDBObject> = bookmarkScreenViewModel.savedData
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_surface)
    ) {
        item {
            SmallTopAppBar(
                modifier = Modifier.layoutId("topBar"),
                title = { Text(text = "Bookmarks", style = MaterialTheme.typography.titleMedium) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface)
            )
        }
        itemsIndexed(savedData) { index, item ->
            SelectedChipComposable(
                imgLink = item.imgURL!!,
                title = item.title!!,
                author = item.author!!,
                index = index,
                indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                indexOnClick = {
                    HomeScreenViewModel.Utils.nonIndexedValue.value = it
                },
                bookMarkText = "Remove From Bookmarks",
                bookMarkIcon = R.drawable.close_icon,
                inBookMarkScreen = true,
                inBookMarkScreenOnClick = {

                }
            )
        }
    }
}