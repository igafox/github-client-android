package com.igafox.githubclient.api

import com.igafox.githubclient.api.response.GetUserRepositories
import com.igafox.githubclient.api.response.SearchUsersResponse
import com.igafox.githubclient.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/users")
    suspend fun getSearchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage:Int,
        @Query("page") page:Int
    ): SearchUsersResponse

    @GET("/users/{user}/repos")
    suspend fun getUserRepositories(
        @Path("user") userId:String,
        @Query("per_page") perPage:Int,
        @Query("page") page:Int
    ):GetUserRepositories

    @GET("/users/{name}")
    suspend fun getUser(
        @Path("name") name:String,
    ):User

}