package com.sakethh.arara

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.customMessageBG
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.unreleased.ImageThing

class CustomThing {
    @Composable
    fun NotConnectedToInternet(
        title: String = "Network, where?",
        description: String = "Uh-oh, it seems mobile-data or wifi is unavailable at this moment. Turn it on \uD83E\uDEE0"
    ) {
        val paddingValue = 20.dp
        Box(
            modifier = Modifier
                .padding(start=paddingValue,end=paddingValue)
                .background(color = Color.DarkGray)
                .fillMaxWidth()
                .requiredHeight(100.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .fillMaxHeight()
                        .wrapContentWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        modifier = Modifier
                            .requiredSize(70.dp)
                            .align(Alignment.CenterStart),
                        contentDescription = "No Internet Connection"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()) {
                    Column(Modifier.padding(end=30.dp)){
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}