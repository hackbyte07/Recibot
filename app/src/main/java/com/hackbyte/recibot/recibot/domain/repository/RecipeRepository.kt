package com.hackbyte.recibot.recibot.domain.repository

import com.hackbyte.recibot.recibot.domain.models.SearchRecipes
import com.hackbyte.recibot.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRandomRecipes(query: String): Flow<Resource<SearchRecipes>>

    suspend fun searchRecipe(query: String): Flow<Resource<SearchRecipes>>
}