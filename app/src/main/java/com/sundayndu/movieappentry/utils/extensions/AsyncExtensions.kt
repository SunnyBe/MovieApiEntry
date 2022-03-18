package com.sundayndu.movieappentry.utils.extensions

import com.sundayndu.movieappentry.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

/**
 * Perform the default operations like onStart, error catching and flow on the provided dispatcher.
 * This is expected to be used in Repository implementation to reduce rewriting these processes for
 * every implemented functions.
 * @param dispatcher to make the Coroutine flow on using the #flowOn()
 * @param cachedData to populate the ResultState with as an initial data before flow collection is
 * done.
 * @return Flow of ResultState.
 */
fun <T> Flow<ResultState<T>>.onStartAndErrorResultState(
    dispatcher: CoroutineDispatcher,
    cachedData: suspend () -> T
): Flow<ResultState<T>> {
    return this.onStart {
        val cachedLatest = cachedData()
        emit(ResultState.Loading(cachedLatest))
    }
        .catch { err ->
            emit(ResultState.Error(err))
        }
        .flowOn(dispatcher)
}

