package com.hackbyte.recibot.recibot.data.remote.recipes.search_recipes


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    @SerialName("idMeal")
    val idMeal: String?, // 52771
    @SerialName("strArea")
    val strArea: String?, // Italian
    @SerialName("strCategory")
    val strCategory: String?, // Vegetarian
    @SerialName("strInstructions")
    val strInstructions: String?, // Bring a large pot of water to a boil. Add kosher salt to the boiling water, then add the pasta. Cook according to the package instructions, about 9 minutes.In a large skillet over medium-high heat, add the olive oil and heat until the oil starts to shimmer. Add the garlic and cook, stirring, until fragrant, 1 to 2 minutes. Add the chopped tomatoes, red chile flakes, Italian seasoning and salt and pepper to taste. Bring to a boil and cook for 5 minutes. Remove from the heat and add the chopped basil.Drain the pasta and add it to the sauce. Garnish with Parmigiano-Reggiano flakes and more basil and serve warm.
    @SerialName("strMeal")
    val strMeal: String?, // Spicy Arrabiata Penne
    @SerialName("strMealThumb")
    val strMealThumb: String?, // https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg
    @SerialName("strTags")
    val strTags: String?, // Pasta,Curry
    @SerialName("strYoutube")
    val strYoutube: String? // https://www.youtube.com/watch?v=1IszT_guI08
)