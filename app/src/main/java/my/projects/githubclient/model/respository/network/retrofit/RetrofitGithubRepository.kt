package my.projects.githubclient.model.respository.network.retrofit

import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.Organisation
import my.projects.githubclient.model.data.Repository
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

    @GET("users/{user}/repos")
    override suspend fun getUserRepos(
        @Path("user") user: String
    ): Response<List<Repository>?>

    @GET("users/{user}/orgs")
    override suspend fun getUserOrgs(
        @Path("user") user: String
    ): Response<List<Organisation>?>

    @GET("/users/{user}/starred")
    override suspend fun getUserStarred(
        @Path("user") user: String
    ): Response<List<Repository>?>

    @GET("/user")
    override suspend fun checkAuth(): Response<String>
}