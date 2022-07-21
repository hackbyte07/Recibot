package com.hackbyte.recibot.recibot.data.local.recipes

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hackbyte.recibot.firestore.data.FirestoreRecipesDao
import com.hackbyte.recibot.firestore.data.FirestoreRecipesDto


@Database(
    entities = [Recipes::class, FirestoreRecipesDto::class],
    version = 2,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

    abstract fun firestoreRecipesDao(): FirestoreRecipesDao

}