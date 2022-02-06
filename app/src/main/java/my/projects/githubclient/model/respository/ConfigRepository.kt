package my.projects.githubclient.model.respository

import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.view.data.Work

interface ConfigRepository {
    suspend fun getAccessToken(): AccessToken?
    suspend fun updateAccessToken(accessToken: AccessToken)
    suspend fun deleteAccessToken()

    suspend fun getSelectedWorks(): List<SelectableObject<Work>>
    suspend fun updateSelectedWorks(works: List<SelectableObject<Work>>)
    suspend fun deleteWorks()
}