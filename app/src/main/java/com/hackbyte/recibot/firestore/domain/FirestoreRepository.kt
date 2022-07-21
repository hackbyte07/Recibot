package com.hackbyte.recibot.firestore.domain

import com.hackbyte.recibot.firestore.data.FirestoreRecipesDto
import com.hackbyte.recibot.util.Resource
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun saveRecipe(firestoreRecipesDto: FirestoreRecipesDto): Flow<Resource<Boolean>>

    suspend fun getFirestoreRecipes(): Flow<Resource<List<FirestoreRecipes>>>

}