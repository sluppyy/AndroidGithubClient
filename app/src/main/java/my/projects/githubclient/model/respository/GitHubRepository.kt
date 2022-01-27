package my.projects.githubclient.model.respository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.projects.githubclient.model.data.OfflineError
import my.projects.githubclient.model.data.UnknownError
import my.projects.githubclient.model.data.Ok
import my.projects.githubclient.model.data.RepositoryResponse
import my.projects.githubclient.model.data.User
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(): GithubRepository {
    @Inject lateinit var networkRepository: NetworkGithubApi

    override suspend fun getUser(
        user: String
    ): Flow<RepositoryResponse<User?>> = flow {
        try {
            val networkResponse = networkRepository.getUser(user)

            if (networkResponse.isSuccessful) emit(Ok(networkResponse.body()))
            else emit(UnknownError<User?>(networkResponse.errorBody().toString()))
        } catch(e: UnknownHostException) { emit(OfflineError<User?>())
        } catch(e: Exception) { emit(UnknownError<User?>(e.toString())) }
    }
}