package com.sakethh.arara.home

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sakethh.arara.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sakethh.arara.home.selectedChipScreen.SelectedChipComposable
import com.sakethh.arara.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val homeScreenViewModel: HomeScreenViewModel = viewModel()
    val headerList = remember { homeScreenViewModel.listForHeader }
    val currentTime = remember { homeScreenViewModel.currentTime }
    val selectedText = remember { mutableStateOf("") }
    val chipIsSelected = remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_dark_surface)
    ) {
        item {
            Text(
                text = currentTime.value,
                fontSize = 23.sp,
                color = md_theme_dark_onSurface,
                modifier = Modifier.padding(top = 50.dp, start = 10.dp, bottom = 5.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(md_theme_dark_surface)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .wrapContentHeight()
                    .animateContentSize()
            ) {
                val onSelected = { text: String ->
                    selectedText.value = text
                }
                if (chipIsSelected.value) {
                    IconButton(onClick = { selectedText.value = "";chipIsSelected.value = false }) {
                        Image(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = "close icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                headerList.forEach {
                    val containerColor = if (selectedText.value == it) {
                        md_theme_dark_onPrimary
                    } else {
                        md_theme_dark_surface
                    }
                    val labelColor = if (selectedText.value == it) {
                        md_theme_dark_primary
                    } else {
                        md_theme_dark_onSurface
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    SuggestionChip(
                        onClick = {
                            chipIsSelected.value = true
                            onSelected(it)
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        },
                        label = { Text(text = it) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = containerColor,
                            labelColor = labelColor
                        )
                    )
                }
            }
        }
        when (selectedText.value) {
            "Warrior-arts" -> {
                items(50) { SelectedChipComposable(it) }
            }
            "News" -> {}
            else -> {
                item {
                    Text(
                        text = "Current iterator is ${selectedText.value}",
                        color = md_theme_dark_primary
                    )
                }
            }
        }

    }
}

