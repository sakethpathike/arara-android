package com.sakethh.arara.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sakethh.arara.R

val generalFont = FontFamily(
    Font(R.font.regular, weight = FontWeight.Normal),
    Font(R.font.light, weight = FontWeight.Light),
    Font(R.font.description, weight = FontWeight.Medium),
    Font(R.font.semibold, weight = FontWeight.SemiBold),
    Font(R.font.bold, weight = FontWeight.Bold)
)
val eddaCaps = FontFamily(Font(R.font.eddacaps, weight = FontWeight.Normal))
val Typography =
    Typography(  // for font towards the text style(heading, description, title and whatever!!)
        titleSmall = TextStyle(
            fontFamily = generalFont,
            fontWeight = FontWeight.Light, fontSize = 16.sp
        ),
        titleMedium = TextStyle(
            fontFamily = generalFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ), // title
        bodySmall = TextStyle(
            fontFamily = generalFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ) /*description and "lyrics" label*/,
        labelSmall = TextStyle(
            fontFamily = eddaCaps,
            fontWeight = FontWeight.Normal, fontSize = 16.sp
        ) // attribute in about page:))
    )
