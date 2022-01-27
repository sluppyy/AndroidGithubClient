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

    init {
        viewModelScope.launch {
            repository.getAuthUser().collect { user ->
                when (user) {
                    is Ok -> _user.value = user.body
                    is UnknownError -> Log.e("dsdsdsd", user.error)
                    is OfflineError -> Log.e("dsdsdsd", "Оффлайн")
                }
            }
        }
    }
}