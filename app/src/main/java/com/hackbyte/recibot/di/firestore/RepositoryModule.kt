package com.hackbyte.recibot.di.firestore

import com.hackbyte.recibot.firestore.data.FirestoreRepositoryImpl
import com.hackbyte.recibot.firestore.domain.FirestoreRepository
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
    abstract fun bindFirestoreRepository(firestoreRepositoryImpl: FirestoreRepositoryImpl): FirestoreRepository

}