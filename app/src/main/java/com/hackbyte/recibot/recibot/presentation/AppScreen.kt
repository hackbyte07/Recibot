package com.hackbyte.recibot.recibot.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.hackbyte.recibot.NavGraphs
import com.hackbyte.recibot.navigation.AppNavGraph
import com.hackbyte.recibot.recibot.presentation.components.BottomScreenNavigation
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@AppNavGraph(start = true)
@Destination
fun AppScreen() {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomScreenNavigation(navController)
        }
    ) {
        it.calculateBottomPadding()
        DestinationsNavHost(navGraph = NavGraphs.bottom, navController = navController)
    }

}