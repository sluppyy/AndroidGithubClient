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
import my.projects.githubclient.model.data.Ok
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.data.UnknownError
import my.projects.githubclient.model.respository.GithubRepository
import javax.inject.Inject

@HiltViewModel
class SearchReposViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): ViewModel() {
    private val _currentSearchedRepos = MutableStateFlow<List<Repository>?>(null)
    val currentSearchedRepos: StateFlow<List<Repository>?> = _currentSearchedRepos

    private val _searchingName = MutableStateFlow("")
    val searchingName: StateFlow<String> = _searchingName

    fun updateSearchingName(newName: String) {
        _searchingName.value = newName
    }

    fun searchRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchingName.value != "") {
                githubRepository.searchRepos(repositoryName = searchingName.value, per_page = 10, page = 1).collect {  repos ->
                    when (repos) {
                        is Ok -> _currentSearchedRepos.emit(repos.body?.items)
                        is UnknownError -> Log.e("errr", repos.error)
                    }
                }
            }
        }
    }
}