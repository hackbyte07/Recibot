package com.hackbyte.recibot.recibot.util

import com.hackbyte.recibot.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber


inline fun <LocalData, RemoteData, ResultData> networkBoundResource(
    crossinline query: () -> Flow<LocalData>,
    crossinline fetchRemoteData: suspend () -> RemoteData,
    crossinline saveRemoteData: suspend (RemoteData) -> Unit,
    crossinline shouldFetchData: (LocalData) -> Boolean = { true },
    crossinline toResultData: LocalData.() -> ResultData
): Flow<Resource<ResultData>> = flow<Resource<ResultData>> {

    val data = query().firstOrNull()

    if (data == null) {
        try {
            saveRemoteData(fetchRemoteData())
            query().map {
                Timber.d(it.toString())
                emit(Resource.Success(toResultData(it)))
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Timber.e(e.message + " Error")
            emit(Resource.Error(e))
        }
    } else if (shouldFetchData(data)) {
        emit(Resource.Loading(loading = true, data = toResultData(data)))
        try {
            saveRemoteData(fetchRemoteData())
            query().map {
                Timber.d(it.toString())
                emit(Resource.Success(toResultData(it)))
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Timber.e(e.message + " Error")
            emit(Resource.Error(e))
        }
    } else {
        query().map {
            emit(Resource.Success(toResultData(it)))
        }
    }


}.flowOn(Dispatchers.IO)