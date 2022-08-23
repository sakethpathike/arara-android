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
import com.sakethh.arara.unreleased.UnreleasedCache
import com.sakethh.arara.unreleased.UnreleasedCache.unreleasedCache
import com.sakethh.arara.unreleased.UnreleasedScreen
import com.sakethh.arara.unreleased.UnreleasedViewModel
import com.sakethh.arara.unreleased.isInternetAvailable
import kotlinx.coroutines.delay

class MainActivity() : ComponentActivity(){
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(typography = Typography /*(typography variable name from Type.kt)*/) {
                Scaffold(floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        if(!isInternetAvailable(this)){
                            CustomThing().CustomBottomSnackBar(image=randomLostInternetImg())
                        }else if(UnreleasedViewModel().rememberMusicPlayer.value){
                            if(isInternetAvailable(this)){
                                CustomThing().MusicPlayerUI()
                            }
                        }
                    }) {
                    UnreleasedScreen()
                }
            }
        }
        unreleasedCache(this)
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MaterialTheme(typography = Typography) {
        CustomThing().MusicPlayerUI()
    }
}