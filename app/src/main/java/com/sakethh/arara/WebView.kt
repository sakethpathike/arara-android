package com.sakethh.arara

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.sakethh.arara.home.shimmer
import com.sakethh.arara.ui.theme.md_theme_dark_onTertiary
import com.sakethh.arara.ui.theme.md_theme_dark_surface
import com.sakethh.arara.ui.theme.md_theme_dark_tertiary

@SuppressLint("ClickableViewAccessibility")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebView(sharedViewModel: SharedViewModel, navController: NavController) {
    BackHandler {
        BottomNavigationBar.isBottomBarHidden.value = false
        navController.popBackStack()
    }
    sharedViewModel.isBottomNavVisible.value=false
    val systemUIController = rememberSystemUiController()
    systemUIController.setStatusBarColor(md_theme_dark_surface)
    val webViewState = rememberWebViewState(url = sharedViewModel._permalink.value)
    val context = LocalContext.current
    val dropDownMenuEnabled = remember { mutableStateOf(false) }
    val webViewNavigator = rememberWebViewNavigator()
    val localClipboardManager = LocalClipboardManager.current
    val localUriHandler = LocalUriHandler.current
    val constraintSet = ConstraintSet {
        val dropDownComposable = createRefFor("dropDownComposable")
        constrain(dropDownComposable) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
    }
    Scaffold(topBar = {
        SmallTopAppBar(
            title = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                        BottomNavigationBar.isBottomBarHidden.value = false
                    }, modifier = Modifier
                        .size(30.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .rotate(180f)
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_dark_surface),
            actions = {
                IconButton(
                    onClick = { dropDownMenuEnabled.value = true }, modifier = Modifier
                        .padding(end = 15.dp)
                        .size(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.more_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        )
    }) {
        Column {
            ConstraintLayout(
                constraintSet = constraintSet,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .layoutId(layoutId = "dropDownComposable")
                        .wrapContentSize()
                ) {
                    DropdownMenu(
                        modifier = Modifier

                            .background(md_theme_dark_onTertiary),
                        expanded = dropDownMenuEnabled.value,
                        onDismissRequest = { dropDownMenuEnabled.value = false }) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Copy link",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = md_theme_dark_tertiary
                                )
                            },
                            onClick = {
                                localClipboardManager.setText(AnnotatedString(sharedViewModel._permalink.value))
                                dropDownMenuEnabled.value = false
                                Toast.makeText(
                                    context,
                                    "link copied to clipboard",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.copy_link),
                                    contentDescription = "Copy link",
                                    modifier = Modifier.size(24.dp)
                                )
                            })
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Open in browser",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = md_theme_dark_tertiary
                                )
                            },
                            onClick = {
                                dropDownMenuEnabled.value = false
                                localUriHandler.openUri(sharedViewModel._permalink.value)
                            },
                            leadingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.open_in_browser),
                                    contentDescription = "Open in browser",
                                    modifier = Modifier.size(24.dp)
                                )
                            })
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                val isWebViewLoaded = remember { mutableStateOf(false) }
                when (webViewState.loadingState) {
                    is LoadingState.Loading -> {
                        isWebViewLoaded.value = true
                    }
                    is LoadingState.Initializing -> {
                        isWebViewLoaded.value = true
                    }
                    is LoadingState.Finished -> {
                        isWebViewLoaded.value = false
                    }
                }
                WebView(
                    navigator = webViewNavigator,
                    state = webViewState,
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmer(visible = isWebViewLoaded.value, cornerSize = CornerSize(0.dp)),
                    onCreated = {
                        it.settings.builtInZoomControls = true
                        it.settings.displayZoomControls = false
                    }
                )
            }
        }
    }
}