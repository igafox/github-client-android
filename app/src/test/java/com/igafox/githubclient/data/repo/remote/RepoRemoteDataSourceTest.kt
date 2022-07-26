package com.igafox.githubclient.data.repo.remote

import com.google.common.truth.Truth.assertThat
import com.igafox.githubclient.AppConstants
import com.igafox.githubclient.api.GitHubApi
import com.igafox.githubclient.data.Result
import com.igafox.githubclient.data.repo.RepoDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(JUnit4::class)
class RepoRemoteDataSourceTest {

    private lateinit var remoteDataSource: RepoDataSource

    @Before
    fun setUp() {

    }

    @Test
    fun getReposByUserName() = runTest  {
        val result = remoteDataSource.getReposByUserName("igafox",1,20)
        assertThat(result).isInstanceOf(Result.Success::class.java)
    }

}