package com.hackbyte.recibot.recibot.data.mapper

import com.hackbyte.recibot.recibot.data.local.recipes.Recipes
import com.hackbyte.recibot.recibot.data.remote.recipes.search_recipes.SearchRecipesDto
import com.hackbyte.recibot.recibot.domain.models.SearchRecipes


fun SearchRecipesDto.toListRecipes(): List<Recipes> = this.meals?.map {
    Recipes(
        it.idMeal ?: "",
        it.strArea,
        it.strCategory,
        it.strInstructions,
        it.strMeal,
        it.strMealThumb,
        it.strTags,
        it.strYoutube
    )
} ?: emptyList()

fun SearchRecipesDto.toSearchRecipes(): SearchRecipes = SearchRecipes(
    this.meals?.map {
        SearchRecipes.Meals(
            it.idMeal ?: "",
            it.strArea ?: "",
            it.strCategory ?: "",
            it.strInstructions ?: "",
            it.strMeal ?: "",
            it.strMealThumb ?: "",
            it.strTags ?: "",
            it.strYoutube ?: ""
        )
    } ?: emptyList()
)