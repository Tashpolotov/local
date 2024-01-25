package com.example.core_utils.base

import com.example.core_utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {

    protected fun <T> doRequest(
        request: suspend () -> T
    ) = flow<Resource<T>> {
        emit(Resource.Success(request()))
    }.flowOn(Dispatchers.IO).catch {
        emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
    }
}