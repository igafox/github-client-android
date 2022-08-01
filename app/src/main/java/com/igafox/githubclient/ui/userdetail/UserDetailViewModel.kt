package com.igafox.githubclient.ui.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.igafox.githubclient.data.repo.RepoRepository
import com.igafox.githubclient.data.user.UserRepository
import com.igafox.githubclient.data.Result.Success
import com.igafox.githubclient.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class UserDetailViewStatus{
    LOADING,
    SUCCESS,
    FAILD
}

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repoRepository: RepoRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _status = MutableStateFlow<UserDetailViewStatus?>(null)
    val status: StateFlow<UserDetailViewStatus?> = _status

    val pagingFlow = _userName
        .filterNotNull()
        .flatMapLatest {
            Pager(PagingConfig(pageSize = 20)) {
                UserDetailPagingSource(repoRepository, it)
            }.flow.cachedIn(viewModelScope)
        }

    fun setUserName(userName: String) {
        _userName.value = userName
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            _status.value = UserDetailViewStatus.LOADING
            val result = userRepository.getUserById(_userName.value)
            if(result is Success) {
                _user.value = result.data
                _status.value = UserDetailViewStatus.SUCCESS
            } else {
                _status.value = UserDetailViewStatus.FAILD
            }
        }
    }

}