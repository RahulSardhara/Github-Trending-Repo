package com.graphybyte.githubtrendingrepo.ui.list

import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity

data class GithubRepoViewState(
    val githubRepoListViewState: GithubRepoListViewState
)

data class GithubRepoListViewState(
    val isLoading: Boolean,
    val githubList: MutableList<GithubEntity>,
    val language: List<String>
)