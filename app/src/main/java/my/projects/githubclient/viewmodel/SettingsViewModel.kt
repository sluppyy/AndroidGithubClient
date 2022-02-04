package my.projects.githubclient.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.model.respository.ConfigRepository
import my.projects.githubclient.model.respository.GithubRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val githubRepository: GithubRepository
): ViewModel() {
    val is_authorized = githubRepository.is_authorized

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
}