package com.igafox.githubclient.data.repo

import com.igafox.githubclient.data.model.User
import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.model.Repo

interface RepoRepository {

    suspend fun getReposByUserName(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<Repo>>

}