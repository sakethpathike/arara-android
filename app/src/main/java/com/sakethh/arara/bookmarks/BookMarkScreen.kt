@file:Suppress("LocalVariableName")

package com.sakethh.arara.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sakethh.arara.R
import com.sakethh.arara.home.HomeScreenViewModel
import com.sakethh.arara.home.selectedChipStuff.SelectedChipComposable
import com.sakethh.arara.randomNoBookmarksImg
import com.sakethh.arara.randomNoBookmarksTxt
import com.sakethh.arara.ui.theme.md_theme_dark_onSurface
import com.sakethh.arara.ui.theme.md_theme_dark_surface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkScreen() {
    val systemUIController = rememberSystemUiController()
    systemUIController.setStatusBarColor(md_theme_dark_surface)
    val bookMarkScreenViewModel: BookMarkScreenViewModel = viewModel()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_surface)
            .padding(bottom = 30.dp)
    ) {
        item {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Bookmarks",
                        style = MaterialTheme.typography.titleMedium,
                        color = md_theme_dark_onSurface
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface)
            )
        }

        if (bookMarkScreenViewModel.bookMarkedDataSize.value == 0) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = randomNoBookmarksImg()),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = randomNoBookmarksTxt(),
                        style = MaterialTheme.typography.titleMedium,
                        color = md_theme_dark_onSurface,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            itemsIndexed(items = bookMarkScreenViewModel.bookMarkedData.component1()) { currentIndex, realmDbObject ->
                SelectedChipComposable(
                    imgLink = realmDbObject.imgURL,
                    title = realmDbObject.title,
                    author = realmDbObject.author,
                    index = currentIndex,
                    indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                    indexOnClick = { int ->
                        HomeScreenViewModel.Utils.nonIndexedValue.value = int
                    },
                    bookMarkText = "Remove From Bookmarks",
                    bookMarkIcon = R.drawable.remove_from_bookmarks,
                    inBookMarkScreen = true,
                    bookmarkOnClick = {
                        bookMarkScreenViewModel.viewModelScope.launch(Dispatchers.Default) {
                            bookMarkScreenViewModel.bookMarkRepo.deleteFromDB(realmDbObject.imgURL)
                            bookMarkScreenViewModel.bookMarkedData.component1()
                        }
                    }
                )
            }
        }


        /*itemsIndexed(BookMarkRepo.BookMarkedData.bookMarkedData) { index, item ->
             SelectedChipComposable(
                 imgLink = item.imgURL,
                 title = item.title,
                 author = item.author,
                 index = index,
                 indexedValue = HomeScreenViewModel.Utils.nonIndexedValue.value,
                 indexOnClick = {
                     HomeScreenViewModel.Utils.nonIndexedValue.value = it
                 },
                 bookMarkText = "Remove From Bookmarks",
                 bookMarkIcon = R.drawable.remove_from_bookmarks,
                 inBookMarkScreen = true,
                 bookmarkOnClick = {

                 }
             )
         }*/
    }
}

