package my.projects.githubclient.model.respository.network

import my.projects.githubclient.model.data.*
import my.projects.githubclient.model.respository.GithubRepository
import retrofit2.Response

interface NetworkGithubApi {
    suspend fun getUser(user: String): Response<User?>
    suspend fun getAuthUser(): Response<AuthUser?>
    suspend fun getUserRepos(user: String): Response<List<Repository>?>
    suspend fun getUserOrgs(user: String): Response<List<Organisation>?>
    suspend fun getUserStarred(user: String): Response<List<Repository>?>

    suspend fun checkAuth(): Response<String>

    suspend fun getGithubFile(
        user: String,
        repository: String,
        path: String
    ): Response<List<GithubFile>>

    suspend fun searchReposByName(repositoryName: String, perPage: Int = 10, page: Int = 1): Response<SearchingRepos>
}