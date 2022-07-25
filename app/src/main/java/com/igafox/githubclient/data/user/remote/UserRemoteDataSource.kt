package com.igafox.githubclient.data.user.remote

import com.igafox.githubclient.api.GitHubApi
import com.igafox.githubclient.data.model.User
import com.igafox.githubclient.data.user.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.Result.Success
import com.igafox.githubclient.data.Result.Error

class UserRemoteDataSource internal constructor(
    private val api: GitHubApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {

    override suspend fun getUsersByName(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<User>> = withContext(ioDispatcher) {
        return@withContext try {
            val result = api.getSearchUsers(query = query, page = page, perPage = maxResults)
            Success(result.items)
        } catch (e: Exception) {
            Error(e)
        }
    }

}