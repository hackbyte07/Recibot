package com.hackbyte.recibot.firestore.data

import androidx.room.withTransaction
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.hackbyte.recibot.firestore.domain.FirestoreRecipes
import com.hackbyte.recibot.firestore.domain.FirestoreRepository
import com.hackbyte.recibot.recibot.data.local.recipes.RecipesDatabase
import com.hackbyte.recibot.recibot.util.networkBoundResource
import com.hackbyte.recibot.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val db: RecipesDatabase
) : FirestoreRepository {

    private val ref = firestore.collection("favourite_recipes")
    private val firestoreDao = db.firestoreRecipesDao()

    override suspend fun saveRecipe(firestoreRecipesDto: FirestoreRecipesDto): Flow<Resource<Boolean>> =
        flow {
            try {
                Timber.d("firestore started")
                ref.document().set(firestoreRecipesDto).await()
                firestoreDao.insert(firestoreRecipesDto)
                emit(Resource.Success(true))
            } catch (e: Throwable) {
                e.printStackTrace()
                Timber.e(e.message + " Error")
                emit(Resource.Error(e))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getFirestoreRecipes(): Flow<Resource<List<FirestoreRecipes>>> =
        networkBoundResource(
            query = {
                firestoreDao.getFirestoreRecipes()
            },
            fetchRemoteData = {
                val recipes = mutableListOf<FirestoreRecipesDto>()
                ref.get().await().map {
                    recipes.add(it.toObject())
                }
                recipes.toList()
            },
            saveRemoteData = {
                db.withTransaction {
                    firestoreDao.deleteAll()
                    firestoreDao.insertAll(it)
                }
            },
            toResultData = {
                this.map {
                    FirestoreRecipes(
                        it.idMeal,
                        it.strArea ?: "",
                        it.strCategory ?: "",
                        it.strInstructions ?: "",
                        it.strMeal ?: "",
                        it.strMealThumb ?: "",
                        it.strTags ?: "",
                        it.strYoutube ?: ""
                    )
                }
            }
        )
}
