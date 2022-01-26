package com.graphybyte.githubtrendingrepo.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.graphybyte.githubtrendingrepo.db.converter.TimestampConverter
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat

@Entity
data class GithubEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long, // 123

    val page: Long, // 123

    val totalPages: Long, // 123

    val name: String?, //"Rahul"

    @SerializedName("full_name")
    val fullName: String?,//"Rahul Sardhara

    @Embedded
    val owner: Owner?, //Owner

    @SerializedName("html_url")
    val htmlUrl: String?,//"https://www.google.com/"

    val description: String?,//"loara loara

    @SerializedName("contributors_url")
    val contributorsUrl: String?,//"https://www.google.com/"

    @TypeConverters(TimestampConverter::class)
    @SerializedName("created_at")
    val createdAt: String?,//25-01-2022 10.30 pm

    @SerializedName("stargazers_count")
    val starsCount: Long?, //123

    val watchers: Long?,//13

    val forks: Long?,//132

    val language: String? //kotlin
):Serializable


data class Owner(
    var login: String?,//"loara

    @SerializedName("avatar_url")
    val avatarUrl: String? //loara
):Serializable
