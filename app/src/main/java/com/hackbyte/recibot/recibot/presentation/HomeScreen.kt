package com.hackbyte.recibot.recibot.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hackbyte.recibot.R
import com.hackbyte.recibot.destinations.AppScreenDestination
import com.hackbyte.recibot.destinations.DetailsScreenDestination
import com.hackbyte.recibot.navigation.AppNavGraph
import com.hackbyte.recibot.navigation.BottomNavGraph
import com.hackbyte.recibot.recibot.presentation.components.RecipeCard
import com.hackbyte.recibot.recibot.util.SearchState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@BottomNavGraph(start = true)
@Destination(route = "home")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    var searchRecipe by remember {
        mutableStateOf("")
    }
    val keyboard = LocalSoftwareKeyboardController.current
    val swipeRefreshState = rememberSwipeRefreshState(viewModel.isRefreshing)
    Scaffold(
        topBar = {
            when (viewModel.topBarState) {
                SearchState.CLOSE -> {
                    SmallTopAppBar(
                        title = { Text(text = "Recibot") },
                        actions = {
                            IconButton(onClick = {
                                viewModel.setBarState(SearchState.EXPANDED)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search"
                                )
                            }
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    )
                }

                SearchState.EXPANDED -> {
                    TextField(
                        value = searchRecipe,
                        onValueChange = { searchRecipe = it },
                        placeholder = {
                            Text(text = "Search Here")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.setBarState(SearchState.CLOSE)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "close"
                                )
                            }
                        },
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboard?.hide()
                                viewModel.searchRecipes(searchRecipe)
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
    ) {

        SwipeRefresh(
            state = swipeRefreshState, onRefresh = {
                viewModel.onRefresh()
            },
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .fillMaxHeight()
        ) {
            LazyColumn {
                items(viewModel.recipes.recipes) { recipes ->
                    Box(contentAlignment = Alignment.TopEnd) {
                        RecipeCard(
                            title = recipes.title,
                            imageUrl = recipes.imageUrl,
                            showPlaceHolder = viewModel.showAnimationPlaceHolder
                        ) {
                            navigator.navigate(
                                DetailsScreenDestination(
                                    youtube = recipes.youtubeUrl
                                )
                            ) {
                                launchSingleTop = true
                            }
                        }
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                            contentDescription = "favourite",
                            modifier = Modifier
                                .padding(top = 15.dp, end = 25.dp)
                                .clickable {
                                    viewModel.addToFavourites(recipes)
                                }
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                }
            }
        }
    }
    SideEffect {
        viewModel.getRandomRecipes()
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
}
