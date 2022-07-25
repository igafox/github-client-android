package com.igafox.githubclient.api.response


import com.google.gson.annotations.SerializedName
import com.igafox.githubclient.data.model.User

data class SearchUsersResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<User>,
    @SerializedName("total_count")
    val totalCount: Int
)