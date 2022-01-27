package my.projects.githubclient.model.respository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.projects.githubclient.model.data.*
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
            else emit(UnknownError<User?>(networkResponse.toString()))
        } catch(e: UnknownHostException) { emit(OfflineError<User?>())
        } catch(e: Exception) { emit(UnknownError<User?>(e.toString())) }
    }

    override suspend fun getAuthUser(): Flow<RepositoryResponse<AuthUser?>> = flow {
        try {
            val networkResponse = networkRepository.getAuthUser()

            if (networkResponse.isSuccessful) emit(Ok(networkResponse.body()))
            else emit(UnknownError<AuthUser?>(networkResponse.toString()))
        } catch(e: UnknownHostException) { emit(OfflineError<AuthUser?>())
        } catch(e: Exception) { emit(UnknownError<AuthUser?>(e.toString())) }
    }
}