package com.sakethh.arara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.unreleased.UnreleasedScreen
import com.sakethh.arara.unreleased.UnreleasedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
                UnreleasedScreen()
           }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {

    }
}