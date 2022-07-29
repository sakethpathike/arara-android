package com.sakethh.arara.unreleased

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarColors
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.github.kittinunf.fuel.core.await
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.sakethh.arara.R
import com.sakethh.arara.ui.theme.firstGradient
import com.sakethh.arara.ui.theme.headerColor
import com.sakethh.arara.ui.theme.secondGradient
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class, DelicateCoroutinesApi::class)
@Composable
fun UnreleasedScreen() {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        isAlwaysCollapsed = false,
        isExpandedWhenFirstDisplayed = true,
        centeredTitleAndSubtitle = false,
        collapsedTopBarHeight = 50.dp,
        expandedTopBarMaxHeight = 70.dp,
    )
    Box(
        modifier = Modifier
            .background(
                headerColor
            )
            .fillMaxSize()
    ) {

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CollapsingTopBar(
                    scrollBehavior = scrollBehavior, colors = CollapsingTopBarColors(
                        backgroundColor = headerColor,
                        contentColor = headerColor,
                        backgroundColorWhenCollapsingOrExpanding = headerColor,
                        onBackgroundColorChange = {}),
                    title = {
                        Text(
                            text = "Unreleased",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Medium
                        )
                    }

                )
            }
        )
        {
            LazyColumn(contentPadding = it, modifier = Modifier.background(headerColor))
            {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        firstGradient, secondGradient
                                    )
                                )
                            )
                    ) {
                        ImageThing(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://ia902501.us.archive.org/9/items/aurora-artworks/unreleaed-artweork.jpg")
                                .crossfade(true).build(),
                            contentDescription = "Toolbar Image",
                            modifier = Modifier
                                .size(150.dp)
                                .shadow(2.dp)
                                .align(Alignment.Center),
                            onError = painterResource(
                                R.drawable.image
                            )
                        )
                    }
                }
               CoroutineScope(Dispatchers.IO).launch {
                    val fetchData = async {
                        val apiUrl = "https://sample-server-side.herokuapp.com/unreleased"
                        val request = apiUrl.httpGet()
                        request.responseJson { _, _, result ->
                            val doc = result.get().array()
                            items(doc.length()) {
                                val imageLink =
                                    doc.getJSONObject(it).getString("specificArtwork")
                                val songName = doc.getJSONObject(it).getString("songName")
                                when (result) {
                                    is Result.Success -> {
                                        SongThing(imageLink = imageLink, songName = songName)
                                    }
                                    is Result.Failure -> {
                                        Toast.makeText(
                                            LocalContext.current,
                                            "Uh-oh, something went wrong!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            }
                        }
                    }
                    fetchData.await()
                }
            }

        }
    }
}
/*
class FirstFragment : Fragment() {
    private lateinit var requestQueue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        requestQueue = Volley.newRequestQueue(this.context)
        loadImage(view.apiImageView)
        view.apiBtn.setOnClickListener {
            loadImage(view.apiImageView)
        }
        return view
    }
"https://sample-server-side.herokuapp.com/unreleased"
    private fun apiCall(imageView: ImageView) {
        lifecycleScope.launch(Dispatchers.IO) {
            val apiUrl = "https://thatcopy.pw/catapi/rest/"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiUrl, null, {
                val imageURL=it.getString("url") // -> Will Get Our Cat Image URL
                imageView.load(imageURL)
            }, {
                Log.d("API Request Error", "${it.printStackTrace()}")
            })
            requestQueue.add(jsonObjectRequest) // -> Added jsonObjectRequest To requestQueue
        }
    }
    private fun loadImage(imageView: ImageView){
        lifecycleScope.launch(Dispatchers.Main){
            apiCall(imageView)
        }
    }
}*/