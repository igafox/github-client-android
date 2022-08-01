package com.igafox.githubclient.di

import com.igafox.githubclient.AppConstants
import com.igafox.githubclient.api.GitHubApi
import com.igafox.githubclient.data.repo.RepoDataSource
import com.igafox.githubclient.data.repo.RepoRepository
import com.igafox.githubclient.data.repo.RepoRepositoryImp
import com.igafox.githubclient.data.repo.remote.RepoRemoteDataSource
import com.igafox.githubclient.data.user.UserDataSource
import com.igafox.githubclient.data.user.UserRepository
import com.igafox.githubclient.data.user.UserRepositoryImp
import com.igafox.githubclient.data.user.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention
annotation class RemoteUserDataSource

@Qualifier
@Retention
annotation class RemoteRepoDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        @RemoteUserDataSource remoteDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImp(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideRepoRepository(
        @RemoteRepoDataSource remoteDataSource: RepoDataSource
    ): RepoRepository {
        return RepoRepositoryImp(remoteDataSource)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @RemoteUserDataSource
    @Provides
    fun provideUserRemoteDataSource(
        api: GitHubApi
    ): UserDataSource {
        return UserRemoteDataSource(api)
    }

    @Singleton
    @RemoteRepoDataSource
    @Provides
    fun provideRepoRemoteDataSource(
        api: GitHubApi
    ): RepoDataSource {
        return RepoRemoteDataSource(api)
    }



}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApi(httpClient: OkHttpClient): GitHubApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.API_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(GitHubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "token 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")
                .build()
            chain.proceed(newRequest)
        }.build()
    }

}