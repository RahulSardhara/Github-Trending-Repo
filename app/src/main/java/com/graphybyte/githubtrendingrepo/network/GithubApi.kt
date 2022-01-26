package com.graphybyte.githubtrendingrepo.network

import com.graphybyte.githubtrendingrepo.network.model.GithubApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    suspend fun getGithubRepos(@Query("sort") sort: String,
                               @Query("order") order: String,
                               @Query("page") page: Long,
                               @Query("q") q: String): Response<GithubApiResponse>

}
