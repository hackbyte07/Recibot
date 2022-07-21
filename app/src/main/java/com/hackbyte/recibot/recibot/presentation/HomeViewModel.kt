package com.hackbyte.recibot.recibot.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackbyte.recibot.firestore.data.FirestoreRecipesDto
import com.hackbyte.recibot.firestore.domain.FirestoreRepository
import com.hackbyte.recibot.recibot.domain.models.SearchRecipes
import com.hackbyte.recibot.recibot.domain.repository.RecipeRepository
import com.hackbyte.recibot.recibot.util.SearchState
import com.hackbyte.recibot.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    var topBarState by mutableStateOf(SearchState.CLOSE)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    fun setBarState(searchState: SearchState) {
        topBarState = searchState
    }

    var isLoading by mutableStateOf(false)
        private set

    var message by mutableStateOf("")
        private set

    var recipes by mutableStateOf(SearchRecipes(emptyList()))
        private set

    private var refreshQuery = ""

    var showAnimationPlaceHolder by mutableStateOf(true)
        private set

    private var showRandomRecipes by mutableStateOf(true)


    fun searchRecipes(query: String) {
        val input = query.trim().lowercase()
        if (input.isEmpty() || input.isBlank())
            return
        refreshQuery = input
        viewModelScope.launch {
            recipeRepository.searchRecipe(input).collect {
                when (it) {
                    is Resource.Loading -> {
                        showAnimationPlaceHolder = false
                        isLoading = it.loading
                    }
                    is Resource.Success -> {
                        showAnimationPlaceHolder = false
                        recipes = it.data ?: SearchRecipes(emptyList())
                    }
                    is Resource.Error -> message = it.throwable?.message ?: "Unknown Error"
                }
            }
        }

    }

    fun addToFavourites(searchRecipeMeals: SearchRecipes.Meals) {
        viewModelScope.launch {
            val firesStoreDto = FirestoreRecipesDto(
                searchRecipeMeals.id,
                searchRecipeMeals.area,
                searchRecipeMeals.category,
                searchRecipeMeals.instructions,
                searchRecipeMeals.title,
                searchRecipeMeals.imageUrl,
                searchRecipeMeals.tags,
                searchRecipeMeals.youtubeUrl
            )
            firestoreRepository.saveRecipe(firesStoreDto).collect {
                when (it) {
                    is Resource.Success -> Timber.d("Added to favourites")
                    else -> {

                    }
                }
            }
        }

    }

    fun getRandomRecipes() {
        if (showRandomRecipes) {
            val random = listOf(
                "Chicken"
            ).random().toString()
            refreshQuery = random
            viewModelScope.launch {
                recipeRepository.getRandomRecipes(random).collect {
                    when (it) {
                        is Resource.Loading -> {
                            showAnimationPlaceHolder = false
                            recipes = it.data ?: SearchRecipes(emptyList())
                        }
                        is Resource.Success -> {
                            showAnimationPlaceHolder = false
                            recipes = it.data ?: SearchRecipes(emptyList())
                        }
                        is Resource.Error -> message = it.throwable?.message ?: "Unknown Error"
                    }
                }
            }
            showRandomRecipes = false
        }
    }

    fun onRefresh() {
        searchRecipes(refreshQuery)
    }

}