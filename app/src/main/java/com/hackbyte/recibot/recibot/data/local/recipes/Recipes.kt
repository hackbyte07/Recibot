package com.hackbyte.recibot.recibot.data.local.recipes

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "searchRecipes")
data class Recipes(
    @PrimaryKey
    val idMeal: String, // 52771

    val strArea: String?, // Italian

    val strCategory: String?, // Vegetarian

    val strInstructions: String?, // Bring a large pot of water to a boil. Add kosher salt to the boiling water, then add the pasta. Cook according to the package instructions, about 9 minutes.In a large skillet over medium-high heat, add the olive oil and heat until the oil starts to shimmer. Add the garlic and cook, stirring, until fragrant, 1 to 2 minutes. Add the chopped tomatoes, red chile flakes, Italian seasoning and salt and pepper to taste. Bring to a boil and cook for 5 minutes. Remove from the heat and add the chopped basil.Drain the pasta and add it to the sauce. Garnish with Parmigiano-Reggiano flakes and more basil and serve warm.

    val strMeal: String?, // Spicy Arrabiata Penne

    val strMealThumb: String?, // https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg

    val strTags: String?, // Pasta,Curry

    val strYoutube: String? // https://www.youtube.com/watch?v=1IszT_guI08
)
