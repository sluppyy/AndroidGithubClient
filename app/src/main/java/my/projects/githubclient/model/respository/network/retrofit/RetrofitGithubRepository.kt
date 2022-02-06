package my.projects.githubclient.model.respository.network.retrofit

import my.projects.githubclient.model.data.*
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/repos/{user}/{repository}/contents/{path}")
    override suspend fun getGithubFile(
        @Path("user") user: String,
        @Path("repository") repository: String,
        @Path("path") path: String
    ): Response<List<GithubFile>>

    @GET("/search/repositories")
    override suspend fun searchReposByName(
        @Query("q") repositoryName: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchingRepos>
}