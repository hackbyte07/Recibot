package com.hackbyte.recibot.recibot.presentation

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.hackbyte.recibot.navigation.AppNavGraph
import com.hackbyte.recibot.navigation.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import timber.log.Timber

@BottomNavGraph
@Destination
@Composable
fun DetailsScreen(
    youtube: String
) {
   Timber.d(youtube)
    val state = rememberWebViewState(url = youtube)
    WebView(state = state, captureBackPresses = false, onCreated = {
        it.settings.javaScriptEnabled = true
    })

}