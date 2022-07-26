package com.igafox.githubclient.ui.search

import androidx.compose.runtime.MutableState
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.igafox.githubclient.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _query = MutableStateFlow<String?>(null)
    val query: StateFlow<String?> = _query

    val pagingFlow = _query
        .filterNotNull()
        .flatMapLatest {
        Pager(PagingConfig(pageSize = 20)) {
            SearchUserPagingSource(userRepository, it)
        }.flow.cachedIn(viewModelScope)
    }

    fun setQuery(query:String) {
        _query.value = query
    }

}