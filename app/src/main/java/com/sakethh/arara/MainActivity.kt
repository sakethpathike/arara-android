package com.sakethh.arara

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sakethh.arara.ui.theme.Typography
import com.sakethh.arara.unreleased.UnreleasedScreen
import com.sakethh.arara.unreleased.UnreleasedViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val unreleasedViewModel: UnreleasedViewModel = viewModel()
            val bottomMsgState= unreleasedViewModel.bottomMsgState
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
                Scaffold(floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        if (bottomMsgState.value) {
                            CustomThing().CustomBottomSnackBar(image=randomLostInternetImg())
                            LaunchedEffect(key1 = "Btm_Msg_hide") {
                                delay(4000L)
                                bottomMsgState.value = false
                            }
                        }
                    }) {
                    UnreleasedScreen(this)
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MaterialTheme(typography = Typography) {

    }
}