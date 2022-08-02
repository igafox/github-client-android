package com.igafox.githubclient.data.user

import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.model.User

class UserRepositoryImp internal constructor(
    private val remoteDataSource: UserDataSource
): UserRepository {

    override suspend fun getUsersByKeyword(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<User>> {
        return remoteDataSource.getUsersByKeyword(query,page,maxResults)
    }

    override suspend fun getUserById(userId: String): Result<User> {
        return remoteDataSource.getUserById(userId)
    }

}