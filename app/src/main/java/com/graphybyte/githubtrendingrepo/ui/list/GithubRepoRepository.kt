package com.graphybyte.githubtrendingrepo.ui.list

import com.graphybyte.githubtrendingrepo.core.NetworkToDBProvider
import com.graphybyte.githubtrendingrepo.core.State
import com.graphybyte.githubtrendingrepo.db.AppDatabase
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity
import com.graphybyte.githubtrendingrepo.network.GithubApi
import com.graphybyte.githubtrendingrepo.network.model.GithubApiResponse
import com.graphybyte.githubtrendingrepo.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class GithubRepoRepository @Inject constructor(
    private val githubAPI: GithubApi,
    private val appDB: AppDatabase,
) {

    fun getGithubRepo(pageNumber: Long): Flow<State<GithubApiResponse>> {
        return object : NetworkToDBProvider<GithubApiResponse>() {
            override suspend fun saveRemoteData(response: GithubApiResponse) {
                withContext(Dispatchers.IO) {
                    val repos = response.items?.map { data ->
                        GithubEntity(
                            id = data.id,
                            page = pageNumber,
                            totalPages = response.items.size.toLong(),
                            name = data.name ?: "",
                            fullName = data.fullName ?: "",
                            owner = data.owner,
                            htmlUrl = data.htmlUrl ?: "",
                            description = data.description ?: "",
                            contributorsUrl = data.contributorsUrl ?: "",
                            createdAt = data.createdAt ?: "",
                            starsCount = data.starsCount ?: 0,
                            watchers = data.watchers ?: 0,
                            forks = data.forks ?: 0,
                            language = data.language ?: ""
                        )
                    }
                    repos?.let {
                        appDB.githubDao().insertRepositories(it)
                    }
                }
            }

            override suspend fun fetchFromLocal(): Flow<GithubApiResponse> {
                return flowOf(GithubApiResponse(0, withContext(Dispatchers.IO) { appDB.githubDao().getRepositoriesByPage(pageNumber) }))
            }

            override suspend fun fetchFromRemote(): Response<GithubApiResponse> {
                return githubAPI.getGithubRepos(AppConstants.SORT, AppConstants.ORDER, pageNumber, AppConstants.PLATFORM)
            }

        }.asFlow()
    }
}
