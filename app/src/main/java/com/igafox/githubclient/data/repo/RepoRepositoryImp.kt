package com.igafox.githubclient.data.repo

import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.model.Repo
import com.igafox.githubclient.data.model.User

class RepoRepositoryImp internal constructor(
    private val remoteDataSource: RepoDataSource
): RepoRepository {

    override suspend fun getReposByUserName(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<Repo>> {
        return remoteDataSource.getReposByUserName(query,page,maxResults)
    }

}