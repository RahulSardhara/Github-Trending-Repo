package com.graphybyte.githubtrendingrepo.core

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber

/**
 * This is abstract wrapper for saving network responses directly to DB (with DAOs)
 */
abstract class NetworkToDBProvider<RESULT> {

    fun asFlow() = flow<State<RESULT>> {

        // Emit Loading State
        emit(State.Loading())

        // Emit Database content first
        emit(State.Success(fetchFromLocal().first()))

        // Fetch latest posts from remote
        val apiResponse = fetchFromRemote()

        // Parse body
        val responseBody = apiResponse.body()
        val errorBody = apiResponse.errorBody()

        // Check for response validation
        if (apiResponse.isSuccessful && responseBody != null) {
            // Save posts into the persistence storage
            saveRemoteData(responseBody)
        } else {
            // Something went wrong! Emit Error state.
            emit(State.ResponseError(code = 400, message = apiResponse.message(), errorBody = errorBody))
        }

        // Retrieve posts from persistence storage and emit
        emitAll(
            fetchFromLocal().map {
                State.Success(it)
            }
        )
    }.catch { e ->
        // Exception occurred! Emit error
        Timber.e("Error -> $e")
        if (e is IllegalStateException) {
            emit(
                State.ExceptionError(
                    errorMessage = "Ohh Crap!, Error with Data Parsing",
                    throwable = e
                )
            )
        }
        e.printStackTrace()
    }

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: RESULT)

    /**
     * Retrieves all data from persistence storage.
     */
    @MainThread
    protected abstract suspend fun fetchFromLocal(): Flow<RESULT>

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RESULT>
}
