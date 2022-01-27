package my.projects.githubclient.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import my.projects.githubclient.model.data.OfflineError
import my.projects.githubclient.model.data.Ok
import my.projects.githubclient.model.data.UnknownError
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
            repository.getUser("sluppyy").collect { user ->
                when (user) {
                    is Ok -> _user.value = user.body
                }
            }
        }
    }
}