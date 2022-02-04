package my.projects.githubclient.model.respository.local

import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.respository.ConfigRepository

interface LocalRepository: ConfigRepository {

    suspend fun getAuthUser(): AuthUser?
    suspend fun updateAuthUser(user: AuthUser)
    suspend fun deleteAuthUser()

    suspend fun getRepos(): List<Repository>
    suspend fun updateRepos(repos: List<Repository>)
    suspend fun deleteRepos()

    override suspend fun getAccessToken(): AccessToken?
    override suspend fun updateAccessToken(accessToken: AccessToken)
    override suspend fun deleteAccessToken()
}