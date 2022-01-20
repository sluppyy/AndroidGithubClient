package my.projects.githubclient.model.respository.network

import my.projects.githubclient.model.data.User
import my.projects.githubclient.model.respository.GithubRepository
import retrofit2.Response

interface NetworkGithubApi {
    suspend fun getUser(user: String): Response<User?>
}