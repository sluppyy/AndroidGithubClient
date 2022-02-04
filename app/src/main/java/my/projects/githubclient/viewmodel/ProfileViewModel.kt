package my.projects.githubclient.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import my.projects.githubclient.R
import my.projects.githubclient.model.data.*
import my.projects.githubclient.model.respository.GithubRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val repository: GithubRepository,
): ViewModel() {

    private val _user = MutableStateFlow<AuthUser?>(null)
    val user: StateFlow<AuthUser?> = _user

    private val _repositories = MutableStateFlow<List<Repository>?>(null)
    val repositories: StateFlow<List<Repository>?> = _repositories

    private val _organisations = MutableStateFlow<List<Organisation>?>(null)
    val organisations: StateFlow<List<Organisation>?> = _organisations

    private val _starred = MutableStateFlow<List<Repository>?>(null)
    val starred:  StateFlow<List<Repository>?> = _starred

    private val _snackBarMessage = MutableStateFlow<Int?>(null)
    val snackBarMessage: StateFlow<Int?> = _snackBarMessage

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    fun updateProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isUpdating.value) {
                _isUpdating.emit(true)

                var resultMessage: Int? = null
                _snackBarMessage.emit(null)

                repository.getAuthUser().collect { user ->
                    when (user) {
                        is Ok -> _user.value = user.body
                        is UnknownError -> resultMessage = R.string.unknown_error
                        is OfflineError -> resultMessage = R.string.offline_error
                        is AuthError    -> resultMessage = R.string.author_error
                    }
                }

                repository.getUserRepos(_user.value?.login ?: "").collect { repositories ->
                    when (repositories) {
                        is Ok -> _repositories.value = repositories.body
                        is UnknownError -> resultMessage = R.string.unknown_error
                        is OfflineError -> resultMessage = R.string.offline_error
                        is AuthError    -> resultMessage = R.string.author_error
                    }
                }

                repository.getOrgs(_user.value?.login ?: "").collect { orgs ->
                    when (orgs) {
                        is Ok -> _organisations.value = orgs.body
                        is UnknownError -> resultMessage = R.string.unknown_error
                        is OfflineError -> resultMessage = R.string.offline_error
                        is AuthError    -> resultMessage = R.string.author_error
                    }
                }

                repository.getStarred(_user.value?.login ?: "").collect { starred ->
                    when (starred) {
                        is Ok -> _starred.value = starred.body
                        is UnknownError -> resultMessage = R.string.unknown_error
                        is OfflineError -> resultMessage = R.string.offline_error
                        is AuthError    -> resultMessage = R.string.author_error
                    }
                }

                _snackBarMessage.emit(resultMessage)
                _isUpdating.emit(false)
            }
        }
    }

    fun clearData() {
        viewModelScope.launch(Dispatchers.IO){
            _user.emit(null)
            _repositories.emit(null)
            _organisations.emit(null)
            _starred.emit(null)
            repository.clearData()
        }
    }

    init {
        updateProfile()
    }
}