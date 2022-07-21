package com.hackbyte.recibot.di.recibot

import android.app.Application
import androidx.room.Room
import com.hackbyte.recibot.recibot.data.local.recipes.RecipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        com.hackbyte.recibot.recibot.data.remote.recipes.HttpClient.client


    @Singleton
    @Provides
    fun provideRecipesDatabase(app: Application): RecipesDatabase = Room.databaseBuilder(
        app.applicationContext,
        RecipesDatabase::class.java,
        "recipes_database"
    ).fallbackToDestructiveMigration()
        .build()

}