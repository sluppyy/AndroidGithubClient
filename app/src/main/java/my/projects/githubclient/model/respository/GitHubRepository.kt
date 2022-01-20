package my.projects.githubclient.model.respository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.projects.githubclient.config.GITHUB_URL
import my.projects.githubclient.model.data.User
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import my.projects.githubclient.model.respository.network.retrofit.RetrofitGithubRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(): GithubRepository {
    @Inject lateinit var networkRepository: NetworkGithubApi

    override suspend fun getUser(
        user: String,
        onError: (String) -> Unit
    ): Flow<User?> = flow {
        try {
            val networkResponse = networkRepository.getUser(user)

            if (networkResponse.isSuccessful) emit(networkResponse.body())
            else onError(networkResponse.errorBody().toString())
        } catch(e: Exception) {
            onError(e.toString())
        }
    }
}