package com.sakethh.arara.home.subHomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.ui.theme.md_theme_dark_onSurface
import com.sakethh.arara.ui.theme.md_theme_dark_surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubHomeScreen(headerText: String="Fanarts") {
    MaterialTheme(typography = Typography) {
        LazyColumn(
            modifier = Modifier
                .background(md_theme_dark_surface)
                .fillMaxSize()
        ) {
            item {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = headerText,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 24.sp,
                            color = md_theme_dark_onSurface
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface)
                )
            }
        }
    }
}