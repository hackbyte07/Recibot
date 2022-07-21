package com.hackbyte.recibot.firestore.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FirestoreRecipesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(firestoreRecipesDto: FirestoreRecipesDto)

    suspend fun insertAll(list: List<FirestoreRecipesDto>) {
        list.forEach {
            insert(it)
        }
    }

    @Delete
    suspend fun deleteOne(firestoreRecipesDto: FirestoreRecipesDto)

    @Query("SELECT * FROM FIRESTORERECIPES")
    fun getFirestoreRecipes(): Flow<List<FirestoreRecipesDto>>

    @Query("DELETE FROM FirestoreRecipes")
    suspend fun deleteAll()

}