package my.projects.githubclient.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.model.respository.ConfigRepository
import my.projects.githubclient.model.respository.GithubRepository
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.utils.Selected
import my.projects.githubclient.view.data.Work
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val githubRepository: GithubRepository
): ViewModel() {
    val isAuthorized = githubRepository.isAuthorized

    private val _selectedWorks: MutableStateFlow<List<SelectableObject<Work>>>
    = MutableStateFlow(
        Work.values().mapNotNull { work ->
            when (work) {
                Work.SETTINGS -> null
                else -> Selected(work)
            }
        }.toList()
    )
    val selectedWorks: StateFlow<List<SelectableObject<Work>>> = _selectedWorks

    private fun checkAuthorization() {
        viewModelScope.launch(Dispatchers.IO) {
            githubRepository.checkAuth()
        }
    }

    fun setAccessToken(accessToken: AccessToken) {
        viewModelScope.launch(Dispatchers.IO) {
            configRepository.updateAccessToken(accessToken = accessToken)
            githubRepository.updateClient()
            checkAuthorization()
        }
    }

    fun clearAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            configRepository.deleteAccessToken()
            githubRepository.updateClient()
            checkAuthorization()
        }
    }

    fun editWork(work: SelectableObject<Work>) {
        viewModelScope.launch {
            _selectedWorks.emit(_selectedWorks.value.map { _work ->
                if (work.obj == _work.obj) work
                else _work
            })
            selectedWorks.value.forEach {
                Log.e("TAG", "write $it ${it.obj.toString}")
            }
            configRepository.updateSelectedWorks(_selectedWorks.value)
        }
    }

    private fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedWorks = configRepository.getSelectedWorks()

            selectedWorks.forEach { Log.e("TAG", "read $it ${it.obj.toString}") }

            //TODO("hardcoded check works size, must just get works")
            if (selectedWorks.size < Work.values().size && selectedWorks.isNotEmpty()) {
                _selectedWorks.emit(selectedWorks)
                Log.e("TAG", "read")
            }
        }
    }

    init {
        update()
    }
}