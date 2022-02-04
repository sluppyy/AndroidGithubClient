package my.projects.githubclient.model.respository.local.room

import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.respository.local.LocalRepository
import my.projects.githubclient.model.respository.local.room.dao.AccessTokenDao
import my.projects.githubclient.model.respository.local.room.dao.AuthUserDao
import my.projects.githubclient.model.respository.local.room.dao.ReposDao

class RoomRepository(
    private val authUserDao: AuthUserDao,
    private val reposDao: ReposDao,
    private val accessTokenDao: AccessTokenDao
): LocalRepository {
    override suspend fun getAuthUser(): AuthUser?
    = authUserDao.getAuthUser()

    override suspend fun updateAuthUser(user: AuthUser)
    = authUserDao.updateAuthUser(user = user)

    override suspend fun deleteAuthUser()
    = authUserDao.clear()

    override suspend fun getRepos(): List<Repository>
    = reposDao.getAllRepos()

    override suspend fun updateRepos(repos: List<Repository>) {
        reposDao.clear()
        reposDao.insertRepos(repos = repos)
    }

    override suspend fun deleteRepos() = reposDao.clear()

    override suspend fun getAccessToken(): AccessToken?
    = accessTokenDao.getAccessToken()

    override suspend fun updateAccessToken(accessToken: AccessToken) {
        accessTokenDao.clear()
        accessTokenDao.insertAccessToken(accessToken = accessToken)
    }

    override suspend fun deleteAccessToken()
    = accessTokenDao.clear()
}