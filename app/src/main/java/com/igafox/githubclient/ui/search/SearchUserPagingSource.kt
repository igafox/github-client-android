package com.igafox.githubclient.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.igafox.githubclient.data.Result.Success
import com.igafox.githubclient.data.Result.Error
import com.igafox.githubclient.data.model.User
import com.igafox.githubclient.data.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUserPagingSource @Inject constructor(
    private val userRepository: UserRepository,
    private val query: String,
) : PagingSource<Int, User>() {

    companion object {
        const val FIRST_PAGE_INDEX = 0
        const val PAGING_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> =
        withContext(Dispatchers.IO) {
            val position = params.key ?: FIRST_PAGE_INDEX
            return@withContext try {
                val result = userRepository.getUsersByName(query, position, PAGING_SIZE)
                if (result is Success) {
                    LoadResult.Page(
                        data = result.data,
                        prevKey = null,
                        nextKey = position + 1
                    )
                } else {
                    val e = result as Error
                    LoadResult.Error(e.exception)
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

}