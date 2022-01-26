package com.graphybyte.githubtrendingrepo.network.model

import com.google.gson.annotations.SerializedName
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity

data class GithubApiResponse(
    @SerializedName("total_count")
    val totalCount: Long?, //123
    @SerializedName("items")
    val items: List<GithubEntity>? //listOf()
)
