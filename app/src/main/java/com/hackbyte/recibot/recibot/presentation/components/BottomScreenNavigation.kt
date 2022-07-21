package com.hackbyte.recibot.recibot.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.hackbyte.recibot.NavGraphs
import com.hackbyte.recibot.appCurrentDestinationAsState
import com.hackbyte.recibot.destinations.AboutScreenDestination
import com.hackbyte.recibot.destinations.FavouriteScreenDestination
import com.hackbyte.recibot.destinations.HomeScreenDestination
import com.hackbyte.recibot.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec


@Composable
fun BottomScreenNavigation(
    navController: NavController
) {

    val items = listOf(
        BottomScreenItems.Home,
        BottomScreenItems.Favourite,
        BottomScreenItems.About
    )

    BottomAppBar {
        val currentRoute: com.hackbyte.recibot.destinations.Destination =
            navController.appCurrentDestinationAsState().value
                ?: NavGraphs.bottom.startAppDestination
        items.forEach {
            this.NavigationBarItem(
                icon = {
                    Icon(imageVector = it.icon, contentDescription = "icon")
                },
                label = {
                    Text(text = it.label)
                },
                selected = currentRoute == it.direction,
                onClick = {
                    navController.navigate(it.direction) {
                        launchSingleTop = true
                        popUpTo(HomeScreenDestination)
                    }
                }
            )
        }
    }
}


sealed class BottomScreenItems(
    val direction: DirectionDestinationSpec,
    val label: String,
    val icon: ImageVector
) {

    object Home : BottomScreenItems(HomeScreenDestination, "Home", Icons.Default.Home)

    object Favourite :
        BottomScreenItems(FavouriteScreenDestination, "Favourite", Icons.Default.Favorite)

    object About : BottomScreenItems(AboutScreenDestination, "About", Icons.Default.Person)

}