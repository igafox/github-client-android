package com.igafox.githubclient.data.user

import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.model.User

class UserRepositoryImp internal constructor(
    private val remoteDataSource: UserDataSource
): UserRepository {

    override suspend fun getUsersByName(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<User>> {
        return remoteDataSource.getUsersByName(query,page,maxResults)
    }

    override suspend fun getUserById(name: String): Result<User> {
        return remoteDataSource.getUserById(name)
    }

}