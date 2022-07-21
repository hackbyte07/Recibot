package com.hackbyte.recibot.di.recibot

import com.hackbyte.recibot.recibot.data.repository.RecipesRepositoryImpl
import com.hackbyte.recibot.recibot.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRecipeRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipeRepository

}