package com.igafox.githubclient.di

import com.igafox.githubclient.AppConstants
import com.igafox.githubclient.api.GitHubApi
import com.igafox.githubclient.data.user.UserDataSource
import com.igafox.githubclient.data.user.UserRepository
import com.igafox.githubclient.data.user.UserRepositoryImp
import com.igafox.githubclient.data.user.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention
annotation class RemoteUserDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBookRepository(
        @RemoteUserDataSource remoteDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImp(remoteDataSource)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @RemoteUserDataSource
    @Provides
    fun provideBookRemoteDataSource(
        api: GitHubApi
    ): UserDataSource {
        return UserRemoteDataSource(api)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApi(): GitHubApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.API_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

}