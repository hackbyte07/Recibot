package com.hackbyte.recibot.recibot.domain.models

data class SearchRecipes(
    val recipes: List<Meals>
) {
    data class Meals(
        val id: String,
        val area: String,
        val category: String,
        val instructions: String,
        val title: String,
        val imageUrl: String,
        val tags: String,
        val youtubeUrl: String
    )
}
