package com.sakethh.arara.unreleased

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SongThing() {
    val paddingValue = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(80.dp)
            .padding(
                start = paddingValue,
                end = paddingValue,
                top = paddingValue,
                bottom = paddingValue
            )
            .border(width = 3.dp, color = Color.DarkGray)
            .background(Color.White)
    ) {
    }
}

@Composable
fun Spacers(modifier: Modifier) {
    Spacer(modifier = modifier)
}