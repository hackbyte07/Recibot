package com.hackbyte.recibot.authentication.data

import com.google.firebase.auth.FirebaseAuth
import com.hackbyte.recibot.authentication.domain.AuthRepository
import com.hackbyte.recibot.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val user = auth.signInWithEmailAndPassword(email, password).await()
            if (user.user?.isEmailVerified!!)
                emit(Resource.Success(true))
            else
                throw IllegalArgumentException("Email not verified")
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e("auth", e.message ?: "An unknown error occurred")
            emit(Resource.Error(e))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val data = auth.createUserWithEmailAndPassword(
                email, password
            ).await()
            data.user?.sendEmailVerification()?.await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e("auth", e.message ?: "An unknown error occurred")
            emit(Resource.Error(e))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override fun signOut(): Boolean {
        auth.signOut()
        return true
    }

    override suspend fun sendResetEmailLink(email: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            auth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e))
        } finally {
            emit(Resource.Loading(false))
        }
    }
}