package com.sakethh.arara

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import com.sakethh.arara.ui.theme.secondGradient
import com.sakethh.arara.unreleased.ImageThing

class CustomThing {
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

    @Composable
    fun MusicPlayerUI(songName: String, imgUrl: String, onClick: () -> Unit) {
        val paddingValue = 20.dp
        Row(
            modifier = Modifier
                .padding(start = paddingValue, end = paddingValue)
                .background(backgroundColor)
                .requiredHeight(65.dp)
                .fillMaxWidth()
                .background(brush = Brush.linearGradient(listOf(firstGradient, secondGradient)))
                .clickable { onClick() }
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(5.dp),
                    color = Color.White
                )
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                ImageThing(
                    model = ImageRequest.Builder(LocalContext.current).data(imgUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image for unreleased song from a warrior:)",
                    modifier = Modifier//gives 10dp padding in top
                        .requiredHeight(45.dp) // renders height of the image
                        .padding(start = 10.dp) //gives 10dp padding in left
                        .requiredWidth(45.dp) //renders width of the image
                        .border(width = 1.dp, color = Color.Gray)
                    , onError = painterResource(randomLostInternetImg())
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 60.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = songName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                    Text(
                        text = "Lyrics",
                        modifier = Modifier
                            .padding(top = 3.dp, bottom = 3.dp)
                            .background(color = Color.Gray)
                            .wrapContentSize()
                            .padding(2.dp),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp, fontWeight = FontWeight.Normal, color = backgroundColor
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