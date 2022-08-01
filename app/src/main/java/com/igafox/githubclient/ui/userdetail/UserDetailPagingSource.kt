package com.igafox.githubclient.ui.userdetail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.igafox.githubclient.data.Result.Success
import com.igafox.githubclient.data.Result.Error
import com.igafox.githubclient.data.model.Repo
import com.igafox.githubclient.data.repo.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDetailPagingSource @Inject constructor(
    private val repoRepository: RepoRepository,
    private val userName: String,
) : PagingSource<Int, Repo>() {

    companion object {
        const val FIRST_PAGE_INDEX = 1
        const val PAGING_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> =
        withContext(Dispatchers.IO) {
            val position = params.key ?: FIRST_PAGE_INDEX
            return@withContext try {
                val result = repoRepository.getReposByUserName(userName, position, PAGING_SIZE)
                if (result is Success) {
                    LoadResult.Page(
                        data = result.data,
                        prevKey = null,
                        //次のページデータがない場合はnullを返してページング終了
                        nextKey = (position + 1).takeIf { result.data.isNotEmpty()}
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