package com.igafox.githubclient.data.user

import com.igafox.githubclient.data.model.User
import com.igafox.githubclient.data.Result

interface UserRepository {

    suspend fun getUsersByKeyword(
        query: String,
        page: Int,
        maxResults: Int
    ): Result<List<User>>

    suspend fun getUserById(
        userId:String
    ):Result<User>

}