package my.projects.githubclient.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import my.projects.githubclient.model.data.*
import my.projects.githubclient.model.respository.GitHubRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val repository: GitHubRepository
): ViewModel() {

    private val _user = MutableStateFlow<AuthUser?>(null)
    val user: StateFlow<AuthUser?> = _user

    private val _repositories = MutableStateFlow<List<Repository>?>(null)
    val repositories: StateFlow<List<Repository>?> = _repositories

    private val _organisations = MutableStateFlow<List<Organisation>?>(null)
    val organisations: StateFlow<List<Organisation>?> = _organisations

    private val _starred = MutableStateFlow<List<Repository>?>(null)
    val starred:  StateFlow<List<Repository>?> = _starred

    private suspend fun updateProfile() {
        repository.getAuthUser().collect { user ->
            when (user) {
                is Ok -> _user.value = user.body
                is UnknownError -> TODO()
                is OfflineError -> TODO()
            }
        }

        repository.getUserRepos(_user.value?.login ?: "").collect { repositories ->
            when (repositories) {
                is Ok -> _repositories.value = repositories.body
                is UnknownError -> TODO()
                is OfflineError -> TODO()
            }
        }

        repository.getOrgs(_user.value?.login ?: "").collect { orgs ->
            when (orgs) {
                is Ok -> _organisations.value = orgs.body
                is UnknownError -> TODO()
                is OfflineError -> TODO()
            }
        }

        repository.getStarred(_user.value?.login ?: "").collect { starred ->
            when (starred) {
                is Ok -> _starred.value = starred.body
                is UnknownError -> TODO()
                is OfflineError -> TODO()
            }
        }
    }

    init {
        viewModelScope.launch {
            updateProfile()
        }
    }
}