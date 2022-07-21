package com.hackbyte.recibot.recibot.data.remote.recipes.search_recipes


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipesDto(
    @SerialName("meals")
    val meals: List<Meal>?
)