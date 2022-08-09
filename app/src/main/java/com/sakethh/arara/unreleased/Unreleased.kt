package com.sakethh.arara.unreleased

import android.util.Log
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.sakethh.arara.ui.theme.backgroundColor
import com.sakethh.arara.ui.theme.headerColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true },
        decayAnimationSpec = rememberSplineBasedDecay()
    )
    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                Text(
                    text = "Unreleased",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.LightGray
                )
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = headerColor)
        )
    }) { contentPadding ->
        LazyColumn(
            modifier = Modifier.background(backgroundColor),
            contentPadding = contentPadding
        ) {
                  Data.retrofit.getData().enqueue(object : Callback<List<UnreleasedIndex>?> {
                      override fun onResponse(
                          call: Call<List<UnreleasedIndex>?>,
                          response: Response<List<UnreleasedIndex>?>
                      ) {
                          items(response.body()!!.size) {
                                  SongThing(
                                      imageLink = response.body()!![it].specificArtwork,
                                      songName = response.body()!![it].songName
                                  )
                          }
                      }

                      override fun onFailure(call: Call<List<UnreleasedIndex>?>, t: Throwable) {
                          Log.d("Crashed here", t.message.toString())
                      }
                  }  )
              }
            }
        }
