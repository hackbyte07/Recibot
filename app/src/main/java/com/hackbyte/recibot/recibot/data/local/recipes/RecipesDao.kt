package com.hackbyte.recibot.recibot.data.local.recipes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(recipes: List<Recipes>)

    @Query("DELETE FROM SEARCHRECIPES")
    suspend fun delete()

    @Query("SELECT * FROM SEARCHRECIPES")
    fun getAllRecipes(): Flow<List<Recipes>>

}