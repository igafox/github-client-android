package com.igafox.githubclient.data.repo.remote

import com.igafox.githubclient.api.GitHubApi
import com.igafox.githubclient.data.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.Result.Success
import com.igafox.githubclient.data.Result.Error
import com.igafox.githubclient.data.model.Repo
import com.igafox.githubclient.data.repo.RepoDataSource

class RepoRemoteDataSource internal constructor(
    private val api: GitHubApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RepoDataSource {

    override suspend fun getReposByUserName(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<Repo>> = withContext(ioDispatcher) {
        return@withContext try {
            val result = api.getUserRepositorys(userId = query, perPage = maxResults, page = page)
            Success(result.toList())
        } catch (e: Exception) {
            Error(e)
        }
    }

}