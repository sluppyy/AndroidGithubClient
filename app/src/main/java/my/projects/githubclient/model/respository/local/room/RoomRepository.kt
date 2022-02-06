package my.projects.githubclient.model.respository.local.room

import android.content.SharedPreferences
import android.util.Log
import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.respository.local.LocalRepository
import my.projects.githubclient.model.respository.local.room.dao.AccessTokenDao
import my.projects.githubclient.model.respository.local.room.dao.AuthUserDao
import my.projects.githubclient.model.respository.local.room.dao.ReposDao
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.utils.checkNull
import my.projects.githubclient.utils.save
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.data.readSelectedObject

class RoomRepository(
    private val authUserDao: AuthUserDao,
    private val reposDao: ReposDao,
    private val accessTokenDao: AccessTokenDao,
    private val sharedPrefs: SharedPreferences
): LocalRepository {

    //============User============
    override suspend fun getAuthUser(): AuthUser? = authUserDao.getAuthUser()
    override suspend fun updateAuthUser(user: AuthUser) = authUserDao.updateAuthUser(user = user)
    override suspend fun deleteAuthUser() = authUserDao.clear()


    //=============Repos============
    override suspend fun getRepos(): List<Repository> = reposDao.getAllRepos()
    override suspend fun updateRepos(repos: List<Repository>) {
        reposDao.clear()
        reposDao.insertRepos(repos = repos)
    }
    override suspend fun deleteRepos() = reposDao.clear()


    //============Access token===========
    override suspend fun getAccessToken(): AccessToken? = accessTokenDao.getAccessToken()
    override suspend fun updateAccessToken(accessToken: AccessToken) {
        accessTokenDao.clear()
        accessTokenDao.insertAccessToken(accessToken = accessToken)
    }
    override suspend fun deleteAccessToken() = accessTokenDao.clear()


    //TODO("RoomRepository shouldn't deal with SharedPreferences")
    //=============Selected works=========
    override suspend fun getSelectedWorks(): List<SelectableObject<Work>> {
        val allWorks = Work.values()
            .filterNot{it == Work.SETTINGS}
            .fold(emptyList<SelectableObject<Work?>?>()) { acc, work ->
                acc + readSelectedObject(sharedPrefs.getString(work.toString, "") ?: "")
            }.mapNotNull { selectedWork -> selectedWork?.checkNull() }
        return allWorks
    }
    override suspend fun updateSelectedWorks(works: List<SelectableObject<Work>>) {
        works.fold(sharedPrefs.edit()) { editor, work ->
            editor.putString(work.obj.toString, work.save())
        }.commit()
    }
    override suspend fun deleteWorks() {
        Work.values().fold(sharedPrefs.edit()) { editor, work ->
            editor.remove(work.toString)
        }.commit()
    }
}