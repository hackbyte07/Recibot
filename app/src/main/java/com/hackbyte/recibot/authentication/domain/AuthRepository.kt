package com.hackbyte.recibot.authentication.domain

import com.hackbyte.recibot.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isUserAuthenticated(): Boolean

    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<Boolean>>

    suspend fun signUpWithEmailAndPassword(email: String, password: String): Flow<Resource<Boolean>>

    fun signOut(): Boolean

    suspend fun sendResetEmailLink(email: String): Flow<Resource<Boolean>>


}