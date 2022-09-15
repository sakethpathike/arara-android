package com.sakethh.arara

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.request.ImageRequest
import com.sakethh.arara.ui.theme.*
import com.sakethh.arara.unreleased.ImageThing
import com.sakethh.arara.unreleased.UnreleasedViewModel


@Composable
    fun CustomBottomSnackBar(
        title: String = "Network, where?",
        description: String = "Uh-oh, it seems mobile-data or wifi is unavailable at this moment.",
        image: Int
    ) {
        val paddingValue = 20.dp
        Box(
            modifier = Modifier
                .padding(start = paddingValue, end = paddingValue)
                .background(color = customMessageBG)
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
                        painter = painterResource(id = image),
                        modifier = Modifier
                            .requiredSize(70.dp)
                            .border(width = 1.dp, color = Color.Gray)
                            .align(Alignment.CenterStart),
                        contentDescription = "No Internet Connection"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()) {
                    Column(Modifier.padding(end = 30.dp)) {
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
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                            lineHeight = 13.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }




fun randomLostInternetImg(): Int {
    return listOf(
        R.drawable.one_no_internet,
        R.drawable.two_no_internet,
        R.drawable.three_no_internet,
        R.drawable.four_no_internet,
        R.drawable.five_no_internet,
        R.drawable.six_no_internet,
        R.drawable.seven_no_internet,
        R.drawable.eight_no_internet,
        R.drawable.nine_no_internet,
        R.drawable.ten_no_internet,
        R.drawable.eleven_no_internet,
        R.drawable.twelve_no_internet
    ).random()
}