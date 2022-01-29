package my.projects.githubclient.model.respository

import kotlinx.coroutines.flow.Flow
import my.projects.githubclient.model.data.*

interface GithubRepository {
    suspend fun getUser(user: String): Flow<RepositoryResponse<User?>>
    suspend fun getAuthUser(): Flow<RepositoryResponse<AuthUser?>>
    suspend fun getUserRepos(user: String): Flow<RepositoryResponse<List<Repository>?>>
    suspend fun getOrgs(user: String): Flow<RepositoryResponse<List<Organisation>?>>
    suspend fun getStarred(user: String): Flow<RepositoryResponse<List<Repository>?>>
}