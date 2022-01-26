package com.graphybyte.githubtrendingrepo.core

/**
 * State Management for UI & Data.
 */
sealed class State<T> {
    class Loading<T> : State<T>() //data in loading state
    data class Success<T>(val data: T) : State<T>() //data is valid
    data class ResponseError<T>(val code: Int,val message: String, val errorBody: Any? = null) : State<T>() //api returned error (403, 404, etc.)
    data class ExceptionError<T>(val errorMessage: String, val throwable: Throwable? = null) : State<T>() //local error (no internet, parsing error, etc.)
}