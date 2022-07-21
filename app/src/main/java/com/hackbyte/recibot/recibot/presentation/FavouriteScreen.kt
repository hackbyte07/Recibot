package com.hackbyte.recibot.recibot.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hackbyte.recibot.destinations.AppScreenDestination
import com.hackbyte.recibot.destinations.DetailsScreenDestination
import com.hackbyte.recibot.navigation.AppNavGraph
import com.hackbyte.recibot.navigation.BottomNavGraph
import com.hackbyte.recibot.recibot.presentation.components.RecipeCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@BottomNavGraph
@Destination
fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(text = "Favourites") })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()), contentAlignment = Alignment.TopCenter
        ) {
            Column() {
                LazyColumn {
                    items(viewModel.firestoreRecipes) { recipe ->
                        RecipeCard(
                            title = recipe.strMeal,
                            imageUrl = recipe.strMealThumb,
                            showPlaceHolder = viewModel.showPlaceHolderAnimation
                        ) {
                            navigator.navigate(
                                DetailsScreenDestination(
                                    youtube = recipe.strYoutube
                                )
                            ) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    }

    SideEffect {
        viewModel.getFavourites()
    }

}