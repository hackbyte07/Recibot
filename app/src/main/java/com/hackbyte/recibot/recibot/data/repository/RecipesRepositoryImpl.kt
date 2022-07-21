package com.hackbyte.recibot.recibot.data.repository

import androidx.room.withTransaction
import com.hackbyte.recibot.recibot.data.local.recipes.RecipesDatabase
import com.hackbyte.recibot.recibot.data.mapper.toListRecipes
import com.hackbyte.recibot.recibot.data.mapper.toSearchRecipes
import com.hackbyte.recibot.recibot.data.remote.recipes.Constants
import com.hackbyte.recibot.recibot.data.remote.recipes.search_recipes.SearchRecipesDto
import com.hackbyte.recibot.recibot.domain.models.SearchRecipes
import com.hackbyte.recibot.recibot.domain.repository.RecipeRepository
import com.hackbyte.recibot.recibot.util.networkBoundResource
import com.hackbyte.recibot.util.Resource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val db: RecipesDatabase,
    private val httpClient: HttpClient
) : RecipeRepository {

    private val recipesDao = db.recipesDao()
    override suspend fun getRandomRecipes(query: String): Flow<Resource<SearchRecipes>> =
        networkBoundResource(
            query = {
                recipesDao.getAllRecipes()
            },
            fetchRemoteData = {
                httpClient.get {
                    Timber.d("network request started")
                    url(Constants.SEARCH_RECIPES.plus("?s=$query"))
                }.body() as SearchRecipesDto
            },
            saveRemoteData = {
                db.withTransaction {
                    recipesDao.delete()
                    recipesDao.insert(it.toListRecipes())
                }
            },
            toResultData = {
                SearchRecipes(
                    this.map {
                        SearchRecipes.Meals(
                            it.idMeal,
                            it.strArea ?: "",
                            it.strCategory ?: "",
                            it.strInstructions ?: "",
                            it.strMeal ?: "",
                            it.strMealThumb ?: "",
                            it.strTags ?: "",
                            it.strYoutube ?: ""
                        )
                    }
                )
            }
        )

    override suspend fun searchRecipe(query: String): Flow<Resource<SearchRecipes>> = flow {
        emit(Resource.Loading(true))
        try {
            val data = httpClient.get {
                Timber.d("network request started")
                url(Constants.SEARCH_RECIPES.plus("?s=$query"))
            }.body() as SearchRecipesDto
            Timber.d(data.meals?.get(0)?.strMeal)
            emit(Resource.Success(data.toSearchRecipes()))
        } catch (e: Throwable) {
            e.printStackTrace()
            Timber.e(e.message + " Error")
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)


}
