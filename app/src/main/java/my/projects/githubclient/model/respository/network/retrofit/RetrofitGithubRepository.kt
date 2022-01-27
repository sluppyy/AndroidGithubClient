package my.projects.githubclient.model.respository.network.retrofit

import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.User
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitGithubRepository : NetworkGithubApi {
    @GET("users/{user}")
    override suspend fun getUser(
        @Path("user") user: String
    ): Response<User?>

    @GET("user")
    override suspend fun getAuthUser(): Response<AuthUser?>
}