package my.projects.githubclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import my.projects.githubclient.model.data.User
import my.projects.githubclient.model.respository.GitHubRepository
import my.projects.githubclient.model.respository.GithubRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val repository: GitHubRepository
): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            repository.getUser("sluppyy").collect { _user.emit(it) }
        }
    }
}