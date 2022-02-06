package my.projects.githubclient.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import my.projects.githubclient.model.data.GithubFile
import my.projects.githubclient.model.data.Ok
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.data.UnknownError
import my.projects.githubclient.model.respository.GithubRepository
import javax.inject.Inject

@HiltViewModel
class RepositoryReviewViewModel @Inject constructor(
    private val gitHubRepository: GithubRepository
): ViewModel() {
    private val _currentFileTree = MutableStateFlow<List<GithubFile>?>(null)
    val currentFileTree: StateFlow<List<GithubFile>?> = _currentFileTree

    private val _currentRepository = MutableStateFlow<Repository?>(null)
    val currentRepository: StateFlow<Repository?> = _currentRepository

    fun openRepository(repository: Repository) {
        _currentRepository.value = repository
        viewModelScope.launch(Dispatchers.IO) {
            if (currentRepository.value != null) {
                gitHubRepository.getGithubFile(
                    repository = currentRepository.value!!,
                    "")
                    .collect { response ->
                        when (response) {
                            is Ok -> _currentFileTree.emit(response.body?.sortedBy { it.type })
                            else -> _currentFileTree.value = null
                        }
                    }
            }
        }
    }

    fun readFile(file: GithubFile) {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentRepository.value != null) {
                gitHubRepository.getGithubFile(
                    repository = currentRepository.value!!,
                    file.path
                ).collect { response ->
                        when (response) {
                            is Ok -> _currentFileTree.emit(response.body?.sortedBy { it.type })
                            else -> _currentFileTree.value = null
                        }
                    }
            }
        }
    }

    /** return false if this be root dir,
    then set in _currentFileTree null
    */
    fun backstack(): Boolean {
        if (currentFileTree.value?.firstOrNull()?.path?.contains("/") == false) {
            _currentFileTree.value = null
            _currentRepository.value = null
            return false
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                if (currentRepository.value != null) {
                    currentFileTree.value
                        ?.firstOrNull()
                        ?.path?.let { path ->
                            Log.e("input", path.split("/").dropLast(2).joinToString("/"))
                            Log.e("input", path)
                            gitHubRepository.getGithubFile(
                                repository = currentRepository.value!!,
                                path = path.split("/").dropLast(2).joinToString("/")
                            ).collect { response ->
                                when (response) {
                                    is Ok -> _currentFileTree.emit(response.body?.sortedBy { it.type })
                                    is UnknownError -> Log.e("debug", response.error)
                                    else -> _currentFileTree.value = null
                                }
                            }
                        }
                }
            }
            return true
        }
    }
}