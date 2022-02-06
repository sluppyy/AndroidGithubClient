package my.projects.githubclient.model.respository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import my.projects.githubclient.model.data.*

interface GithubRepository {
    val isAuthorized: StateFlow<Boolean>

    suspend fun getUser(user: String)
    : Flow<RepositoryResponse<User?>>

    suspend fun getAuthUser()
    : Flow<RepositoryResponse<AuthUser?>>

    suspend fun getUserRepos(user: String)
    : Flow<RepositoryResponse<List<Repository>?>>

    suspend fun getOrgs(user: String)
    : Flow<RepositoryResponse<List<Organisation>?>>

    suspend fun getStarred(user: String)
    : Flow<RepositoryResponse<List<Repository>?>>

    suspend fun checkAuth()
    suspend fun updateClient()
    suspend fun clearData()

    suspend fun getGithubFile(repository: Repository, path: String)
    : Flow<RepositoryResponse<List<GithubFile>?>>

    suspend fun searchRepos(repositoryName: String, per_page:Int, page: Int)
    : Flow<RepositoryResponse<SearchingRepos?>>
}