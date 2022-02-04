package my.projects.githubclient.model.respository

import my.projects.githubclient.model.data.AccessToken

interface ConfigRepository {
    suspend fun getAccessToken(): AccessToken?
    suspend fun updateAccessToken(accessToken: AccessToken)
    suspend fun deleteAccessToken()
}