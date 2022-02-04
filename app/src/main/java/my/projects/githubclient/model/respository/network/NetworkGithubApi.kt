package my.projects.githubclient.model.respository.network

import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.Organisation
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.data.User
import my.projects.githubclient.model.respository.GithubRepository
import retrofit2.Response

interface NetworkGithubApi {
    suspend fun getUser(user: String): Response<User?>
    suspend fun getAuthUser(): Response<AuthUser?>
    suspend fun getUserRepos(user: String): Response<List<Repository>?>
    suspend fun getUserOrgs(user: String): Response<List<Organisation>?>
    suspend fun getUserStarred(user: String): Response<List<Repository>?>

    suspend fun checkAuth(): Response<String>
}