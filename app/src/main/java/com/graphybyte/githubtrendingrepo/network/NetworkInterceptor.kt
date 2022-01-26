package com.graphybyte.githubtrendingrepo.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * This is a network interceptor
 * This is used for adding additional params to all the request made via app.
 */
class NetworkInterceptor @javax.inject.Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed( chain.request().newBuilder().build())
    }
}