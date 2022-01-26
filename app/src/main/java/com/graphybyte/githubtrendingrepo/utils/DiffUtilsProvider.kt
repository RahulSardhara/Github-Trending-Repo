package com.graphybyte.githubtrendingrepo.utils

import androidx.recyclerview.widget.DiffUtil
import com.graphybyte.githubtrendingrepo.db.entity.GithubEntity


val eventsDiffUtil = object : DiffUtil.ItemCallback<GithubEntity>() {
    override fun areItemsTheSame(
        oldItem: GithubEntity,
        newItem: GithubEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GithubEntity,
        newItem: GithubEntity
    ): Boolean {
        return oldItem == newItem
    }
}

val languageDiffUtil = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}


